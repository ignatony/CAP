/**
 * 
 */
package com.cap.engine.component;

import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.Exchanger;
import com.cap.engine.model.Flow;
import com.cap.engine.model.ResMessage;

/**
 * @author Ignatious
 *
 */
public interface PrepareComponent {
	
	public ResMessage prepare(Exchanger exchanger, Flow flow) throws CapEngineException;

}
