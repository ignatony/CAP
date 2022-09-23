/**
 * 
 */
package com.cap.engine.transformation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.Exchanger;
import com.cap.engine.model.Transform;
import com.cap.engine.service.TransFileStorageService;
import com.cap.engine.utils.FileUtils;

import io.atlasmap.api.AtlasContext;
import io.atlasmap.api.AtlasContextFactory;
import io.atlasmap.api.AtlasSession;
import io.atlasmap.core.DefaultAtlasContextFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Component
@Slf4j
public class ADMTransformation {
	@Autowired
	TransFileStorageService transFileStorageService;
	@Autowired
	FileUtils fileUtils;

	@Value("${file.fetch-dir}")
	private String dir;

	public String perpare(Transform transform, Exchanger exchanger) throws CapEngineException {
		log.info("ADMTransformation::perpare");
		String outPayload = null;

		String mapperFileName = transform.getTransformFileName().contains(".") ? transform.getTransformFileName()
				: transform.getTransformFileName() + "." + transform.getTransformType().toLowerCase();


		byte[] mapperData = transFileStorageService.getTransFileBytes(exchanger.getCustomerId(),
				exchanger.getProviderId(), exchanger.getServiceId(), mapperFileName);

		if (null == mapperData) {
			log.info("ADMTransformation::perpare:: mapper file is null");
			throw new CapEngineException("ADMTransformation::perpare:: mapper file is null");
		}
		String sourceFileName = transform.getSourceFileName().contains(".") ? transform.getSourceFileName()
				: transform.getSourceFileName() + "." + transform.getSourceFileType().toLowerCase();

		try {
			// mapper file writing
			fileUtils.fileWriteByteArray(transform.getTransformFileName(), mapperData,
					transform.getTransformType().toLowerCase());

			// Source file writing
			fileUtils.fileWrite(sourceFileName, exchanger.getNewPayload());
		} catch (IOException e1) {
			log.info("ADMTransformation::perpare:: file writing error " + e1.getMessage());
			throw new CapEngineException("ADMTransformation::perpare:: file writing error " + e1.getMessage());
		}

		try {
			
			
			outPayload = transform(mapperFileName, sourceFileName, sourceFileName);
		} catch (Exception e) {
			log.info("ADMTransformation::perpare:: mapper Transform error " + e.getMessage());
			throw new CapEngineException("ADMTransformation::perpare:: mapper Transform error " + e.getMessage());
		}

		return outPayload; // transform( admFile, sourceFile, sourceDocument);
	}

	public String transform(String mapperFileName, String sourceFileName, String sourceDocument) throws Exception {

		File admFile = new File(dir + mapperFileName);
		File sourceFile = new File(dir + sourceFileName);
		
	
		if(sourceFileName.contains(".")) 
			sourceDocument= FilenameUtils.removeExtension(sourceDocument);

		// URL url =
		// Thread.currentThread().getContextClassLoader().getResource("address.adm");
		// URL url =
		// Thread.currentThread().getContextClassLoader().getResource(admFile);
		AtlasContextFactory factory = DefaultAtlasContextFactory.getInstance();
		AtlasContext context = factory.createContext(admFile);
		AtlasSession session = context.createSession();
		// System.out.println("Source document:\n" +
		// session.getDefaultTargetDocument());
		// url =
		// Thread.currentThread().getContextClassLoader().getResource("addressSource.json");
		// url = Thread.currentThread().getContextClassLoader().getResource(sourceFile);
		String source = new String(Files.readAllBytes(Paths.get(sourceFile.toURI())));
		log.info("Source document:\n" + source);

		// session.setSourceDocument("addressSource", source);
		session.setSourceDocument(sourceDocument, source);
		context.process(session);
		// String targetDoc = (String) session.getTargetDocument("XMLInstanceSource");
		String targetDoc = (String) session.getDefaultTargetDocument();

		log.info("Source document:\n" + targetDoc);

		return targetDoc;
	}

}
