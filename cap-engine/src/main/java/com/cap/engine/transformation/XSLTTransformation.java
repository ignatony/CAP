package com.cap.engine.transformation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.Exchanger;
import com.cap.engine.model.Transform;
import com.cap.engine.service.TransFileStorageService;
import com.cap.engine.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Slf4j
@Component
public class XSLTTransformation {

	@Autowired
	TransFileStorageService transFileStorageService;
	@Autowired
	FileUtils fileUtils;
	@Value("${file.fetch-dir}")
	private String dir;

	/**
	 * @param transform
	 * @param exchanger
	 * @return
	 * @throws CapEngineException
	 */
	public String perpare(Transform transform, Exchanger exchanger) throws CapEngineException {
		log.info("XSLTTransformation::perpare");
		String outPayload = null;

		String mapperFileName = transform.getTransformFileName().contains(".") ? transform.getTransformFileName()
				: transform.getTransformFileName() + "." + transform.getTransformType().toLowerCase();

		// String mapperFileName = transform.getMapperFileName() + "." +
		try {
			// transform.getMapperType().toLowerCase();
			byte[] xsltData = transFileStorageService.getTransFileBytes(exchanger.getCustomerId(),
					exchanger.getProviderId(), exchanger.getServiceId(), mapperFileName);

			if (null == xsltData) {
				log.info("XSLTTransformation::perpare:: mapper file is null");
				throw new CapEngineException("ADMTransformation::perpare:: mapper file is null");
			}

			// mapper file writing
			fileUtils.fileWriteByteArray(transform.getTransformFileName(), xsltData,
					transform.getTransformType().toLowerCase());
		} catch (IOException e2) {
			log.info("XSLTTransformation::perpare:: file writing error " + e2.getMessage());
			throw new CapEngineException("XSLTTransformation::perpare:: file writing error " + e2.getMessage());
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try (InputStream is = new FileInputStream(new File(dir + mapperFileName))) {

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(is);

			outPayload = transform(doc, exchanger.getNewPayload());
			exchanger.setNewPayload(outPayload);

		} catch (IOException | ParserConfigurationException | SAXException e) {
			log.info("XSLTTransformation::perpare:: xslt doc prepare  error " + e.getMessage());
			throw new CapEngineException("XSLTTransformation::perpare::xslt doc prepare error " + e.getMessage());
		}
		return outPayload;
	}

	/**
	 * @param doc
	 * @param sourceData
	 * @return
	 * @throws CapEngineException
	 */
	private String transform(Document doc, String sourceData) throws CapEngineException {

		String resMessage = null;

		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

// add XSLT in Transformer
			Transformer transformer = transformerFactory.newTransformer(new StreamSource(new StringReader(sourceData)));

			StringWriter outPutWriter = new StringWriter();
			Result result = new StreamResult(outPutWriter);

			transformer.transform(new DOMSource(doc), result);
			resMessage = outPutWriter.toString();
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			log.info("XSLTTransformation::transform:: xslt  Transformer   error " + e.getMessage());
			throw new CapEngineException("XSLTTransformation::transform:: xslt Transformer  error " + e.getMessage());
		}

		return resMessage;
	}

}
