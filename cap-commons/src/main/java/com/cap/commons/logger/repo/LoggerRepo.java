/**
 * 
 */
package com.cap.commons.logger.repo;

import java.util.List;

import com.cap.commons.logger.model.Logger;

/**
 * @author Ignatious
 *
 */
public interface LoggerRepo {

	public void add(Logger logger);

	public List<Logger> findAllLogger();

	public List<Logger> findLoggerByFieldType(String type, String findStr);

	public void deleteAllLogger();

	//public void storeAll();

}
