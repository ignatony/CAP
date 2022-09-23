/**
 * 
 */
package com.cap.engine.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cap.engine.config.FileStorageConfig;
import com.cap.engine.constant.CapEngineConstant;
import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.FileMetaData;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Component
@Slf4j
public class FileUtils {

	private final Path fileStorageLocation;

	@Autowired(required = true)
	public FileUtils(FileStorageConfig fileStorageProperties) {

		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Path path = Files.createDirectories(this.fileStorageLocation);

		} catch (Exception ex) {
			log.error("FileUtils :: storeFile :: invalid Path :: " + ex);
			throw new CapEngineException(
					CapEngineConstant.COULD_NOT_CREATE_THE_DIRECTORY_WHERE_THE_UPLOADED_FILES_WILL_BE_STORED, ex);
		}
	}

	/**
	 * @param file
	 * @return
	 * @throws NiceFileException
	 */
	public FileMetaData storeFile(MultipartFile file) throws CapEngineException {
		log.info("FileUtils::inside storeFile");
		FileMetaData fileData = null;
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		log.info("file Name is -----> {}", fileName);
		try {

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			fileData = new FileMetaData();
			fileData.setFileName(fileName);
			fileData.setSize(file.getSize());
			fileData.setMime(file.getContentType());
			
			log.info("file Store Done !");

		} catch (IOException ex) {
			log.error("FileUtils :: storeFile :: " + ex);
			throw new CapEngineException(CapEngineConstant.COULD_NOT_STORE_FILE + fileName
					+ CapEngineConstant.PLEASE_TRY_AGAIN, ex);
		}
		return fileData;
	}

	/**
	 * @param fileName
	 */
	public void fileDelete(String fileName) throws CapEngineException {
		Path fileToDeletePath = this.fileStorageLocation.resolve(fileName);
		try {
			Files.delete(fileToDeletePath);
		} catch (IOException e) {
			log.error("FileUtils :: fileDelete :: " + e.getMessage());
			throw new CapEngineException("FileUtils :: fileDelete :: " + e.getMessage());
		}
	}

	/**
	 * @param fileName
	 * @param content
	 * @param extention
	 * @throws IOException
	 */
	public void fileWrite(String fileName, String content) throws IOException {
		
		//String fname = fileName + "." + extention;

		Path targetLocation = this.fileStorageLocation.resolve(fileName);
		try {
			Files.write(targetLocation, content.getBytes());
		} catch (IOException e) {
			log.error("FileUtils :: fileWrite :: " + e);
			throw new CapEngineException("FileUtils :: fileWrite :: " + e.getMessage());
		}
	}
	
	public void fileWriteByteArray(String fileName, byte[] content, String extention) throws IOException {
		//String fname = fileName + "." + extention;

		Path targetLocation = this.fileStorageLocation.resolve(fileName);
		try {
			Files.write(targetLocation, content);
		} catch (IOException e) {
			log.error("FileUtils :: fileWrite :: " + e);
			throw new CapEngineException("FileUtils :: fileWrite :: " + e.getMessage());
		}
	}

	/**
	 * @param fileName
	 * @param content
	 * @param extention
	 * @throws IOException
	 */
	public void fileWriteRequest(String fileName, String content, String extention) throws IOException {
		String fname = fileName + "_1." + extention;

		Path targetLocation = this.fileStorageLocation.resolve(fname);
		try {
			Files.write(targetLocation, content.getBytes());
		} catch (IOException e) {
			log.error("FileUtils :: fileWrite :: " + e);
			throw new CapEngineException("FileUtils :: fileWrite :: " + e.getMessage());
		}
	}

}
