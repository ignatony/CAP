package com.cap.engine.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response implements Serializable{
	
	private static final long serialVersionUID = 3069036580797423819L;

	public Response() {
		
	}
	private String correlationId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
	private LocalDateTime startDateTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
	private LocalDateTime endDateTime;

	private String status;

	private String message;

	private String payload;

	private boolean last;
}
