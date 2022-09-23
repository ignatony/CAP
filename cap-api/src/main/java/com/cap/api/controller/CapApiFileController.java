/**
 * 
 */
package com.cap.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cap.api.exception.CapAPIException;
import com.cap.api.model.FileInfo;
import com.cap.api.model.FileResponseMessage;
import com.cap.api.model.TransformationFile;
import com.cap.api.model.UploadFileResponse;
import com.cap.api.model.User;
import com.cap.api.service.TransFileStorageService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@RequestMapping(path = "${api.base.url}")
@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
@RestController
@Slf4j
public class CapApiFileController {

	@Autowired
	private TransFileStorageService transFileStorageService;

	@PostMapping("/uploadFile/{providerId}/{serviceId}")
	public UploadFileResponse uploadFile(@PathVariable int providerId, @PathVariable int serviceId,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) throws CapAPIException {

		User user = (User) request.getAttribute("USER");
		TransformationFile dbFile = transFileStorageService.storeFile(file, providerId, serviceId, user);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/capFileDownload/" + providerId + "/" + serviceId + "/").path(dbFile.getFileName())
				.toUriString();

		return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultiFiles/{providerId}/{serviceId}")
	public List<UploadFileResponse> uploadMultipleFiles(@PathVariable int providerId, @PathVariable int serviceId,
			@RequestParam("files") MultipartFile[] files, HttpServletRequest request) throws CapAPIException {
		log.info("CapApiFileController:: uploadMultipleFiles");
		return Arrays.asList(files).stream().map(file -> {
			try {
				return uploadFile(providerId, serviceId, file, request);
			} catch (CapAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}

	@GetMapping("/capFileDownload/{providerId}/{serviceId}/{fileName}")
	public ResponseEntity<Resource> downloadFileByName(@PathVariable int providerId, @PathVariable int serviceId,
			@PathVariable String fileName) {
		// Load file from database
		TransformationFile dbFile = transFileStorageService.getTransFile(providerId, serviceId, fileName);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}

	@GetMapping("/capFileDownload/{providerId}/{serviceId}")
	public ResponseEntity<Resource> downloadFileById(@PathVariable int providerId, @PathVariable int serviceId) {
		// Load file from database
		TransformationFile dbFile = transFileStorageService.getTransFileById(providerId, serviceId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}

	@GetMapping("/capFile/{providerId}/{serviceId}")
	public ResponseEntity<List<FileInfo>> getFile(@PathVariable int providerId, @PathVariable int serviceId) {

		List<FileInfo> fileInfos = new ArrayList<>();
		Resource file = null;
		List<TransformationFile> transformationFiles = transFileStorageService.getListFileById(providerId, serviceId);
		for (TransformationFile dbFile : transformationFiles) {
			file = new ByteArrayResource(dbFile.getData());

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/capFileDownload/" + providerId + "/" + serviceId + "/").path(dbFile.getFileName())
					.toUriString();

			fileInfos.add(new FileInfo(dbFile.getFileName(), fileDownloadUri));

		}
		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}

	@PostMapping("/insertFile/{providerId}/{serviceId}")
	public ResponseEntity<FileResponseMessage> insertFile(@PathVariable int providerId, @PathVariable int serviceId,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) throws CapAPIException {
		String message = null;
		try {
			User user = (User) request.getAttribute("USER");
			TransformationFile dbFile = transFileStorageService.storeFile(file, providerId, serviceId, user);

			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new FileResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileResponseMessage(message));
		}
	}

}
