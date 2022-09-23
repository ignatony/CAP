package com.cap.transport.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class Response {

	private String correlationId;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
//	private LocalDateTime startDateTime;
//
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
//	private LocalDateTime endDateTime;

	private String status;

	private String message;

	private String payload;

	private boolean last;

}
