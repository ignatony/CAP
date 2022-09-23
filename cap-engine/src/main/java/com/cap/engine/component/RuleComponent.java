/**
 * 
 */
package com.cap.engine.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cap.engine.constant.CapEngineConstant;
import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.Exchanger;
import com.cap.engine.model.Flow;
import com.cap.engine.model.ResMessage;
import com.cap.engine.model.Rule;
import com.cap.engine.model.Transform;
import com.cap.engine.rule.JavaScriptRule;
import com.cap.engine.rule.PythonRule;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Component
@Slf4j
public class RuleComponent implements PrepareComponent {
	@Autowired
	JavaScriptRule javaScriptRule;
	@Autowired
	PythonRule pythonRule;

	@Override
	public ResMessage prepare(Exchanger exchanger, Flow flow) throws CapEngineException {
		log.info("RuleComponent::prepare");
		ResMessage resMessage = null;
		Rule rule = flow.getRule();

		if (null == rule) {
			log.error("RuleComponent::prepare::rule is null");
			throw new CapEngineException("RuleComponent::prepare::rule is null");
		}

		if (rule.getRuleType().equalsIgnoreCase(CapEngineConstant.PYTHON)) {
			resMessage = pythonRulePrepare(rule, exchanger);
		} else if (rule.getRuleType().equalsIgnoreCase(CapEngineConstant.JAVASCRIPT)) {
			resMessage = javaScriptRulePrepare(rule, exchanger);
		}
		return resMessage;
	}

	/**
	 * @param rule
	 * @param exchanger
	 * @return
	 * @throws CapEngineException
	 */
	private ResMessage pythonRulePrepare(Rule rule, Exchanger exchanger) throws CapEngineException {
		log.info("RuleComponent::pythonRulePrepare::pythonRule");
		ResMessage resMessage = new ResMessage();
		resMessage.setCorrelationId(exchanger.getCorrelationId());

		String outpayload = pythonRule.execute(rule, exchanger);

		if (outpayload != null) {
			resMessage.setOutPayload(outpayload);
			resMessage.setStatus("Success");
			exchanger.setNewPayload(outpayload);
		} else {
			resMessage.setStatus("Failed");
		}

		return resMessage;
	}

	/**
	 * @param rule
	 * @param exchanger
	 * @return
	 * @throws CapEngineException
	 */
	private ResMessage javaScriptRulePrepare(Rule rule, Exchanger exchanger) throws CapEngineException {
		log.info("RuleComponent::javaScriptRulePrepare::javaScriptRule");
		ResMessage resMessage = new ResMessage();
		resMessage.setCorrelationId(exchanger.getCorrelationId());

		String outpayload = javaScriptRule.execute(rule, exchanger);

		if (outpayload != null) {
			resMessage.setOutPayload(outpayload);
			resMessage.setStatus("Success");
			exchanger.setNewPayload(outpayload);
		} else {
			resMessage.setStatus("Failed");
		}

		return resMessage;
	}

}
