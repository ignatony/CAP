/**
 * 
 */
package com.cap.engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.cap.engine.constant.CapEngineConstant;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ignatious
 *
 */
@ConfigurationProperties(prefix = CapEngineConstant.FILE)
@Data
@NoArgsConstructor
@Component
public class FileStorageConfig {

	private String uploadDir;
}
