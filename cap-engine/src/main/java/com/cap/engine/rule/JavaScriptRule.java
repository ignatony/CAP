/**
 * 
 */
package com.cap.engine.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cap.engine.model.Exchanger;
import com.cap.engine.model.ResMessage;
import com.cap.engine.model.Rule;
import com.cap.engine.rules.JavaScriptRuleExecution;

/**
 * @author Ignatious
 *
 */
@Component
public class JavaScriptRule {
	@Autowired
	JavaScriptRuleExecution javaScriptRuleExecution;

	public ResMessage execute() {
		// TODO Auto-generated method stub
		return null;
	}

	public String execute(Rule rule, Exchanger exchanger) {
		// TODO Auto-generated method stub
		return null;
	}


}
