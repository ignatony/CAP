/**
 * 
 */
package com.cap.engine.component;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.Exchanger;
import com.cap.engine.model.Flow;
import com.cap.engine.model.ResMessage;
import com.cap.engine.model.Transform;
import com.cap.engine.transformation.ADMTransformation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Component
@Slf4j
public class TransformComponent implements PrepareComponent {
	@Autowired
	ADMTransformation aDMTransformation;

	@Override
	public ResMessage prepare(Exchanger exchanger, Flow flow) throws CapEngineException {
		log.info("TransformComponent::prepare");
		ResMessage resMessage = null;
		Transform transform = flow.getTransform();
		if (null == transform) {
			log.error("TransformComponent::prepare::transform is null");
			throw new CapEngineException("TransformComponent::prepare::transform is null");
		}

		if (transform.getTransformType().equalsIgnoreCase("XSLT")) {
			resMessage = xsltTranform(transform, exchanger);
		} else if (transform.getTransformType().equalsIgnoreCase("ADM")) {
			resMessage = admTranform(transform, exchanger);
		} else {
			resMessage = jsonTranform(transform, exchanger);
		}
		return resMessage;
	}

	private ResMessage jsonTranform(Transform transform, Exchanger exchanger) throws CapEngineException {
		log.info("TransformComponent::prepare::jsonTranform");
		return null;
	}

	private ResMessage admTranform(Transform transform, Exchanger exchanger) throws CapEngineException {
		log.info("TransformComponent::prepare::admTranform");
		ResMessage resMessage = new ResMessage();
		resMessage.setCorrelationId(exchanger.getCorrelationId());

		String outpayload = aDMTransformation.perpare(transform, exchanger );

		if (outpayload != null) {
			resMessage.setOutPayload(outpayload);
			resMessage.setStatus("Success");
			exchanger.setNewPayload(outpayload);
		} else {
			resMessage.setStatus("Failed");
		}

		return resMessage;
	}

	private ResMessage xsltTranform(Transform transform, Exchanger exchanger) throws CapEngineException {
		log.info("TransformComponent::prepare::xsltTranform");
		return null;
	}

}
