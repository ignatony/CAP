/**
 * 
 */
package com.cap.engine.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.Exchanger;
import com.cap.engine.model.ResMessage;
import com.cap.engine.model.Rule;
import com.cap.engine.rules.PythonRuleExecution;

/**
 * @author Ignatious
 *
 */
@Component
public class PythonRule {
	@Autowired
	PythonRuleExecution pythonRuleExecution;


	public String execute(Rule rule, Exchanger exchanger) {
		// TODO Auto-generated method stub
		return null;
	}

}
