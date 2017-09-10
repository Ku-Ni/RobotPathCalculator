package co.uk.coenie.robot_path_calculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse implements Response {
	@JsonProperty("ExceptionClass")
	private String exceptionClass;
	@JsonProperty("ExceptionMessage")
	private String exceptionMessage;

	public ErrorResponse(Exception e) {
		exceptionClass = e.getClass().getName();
		exceptionMessage = e.getMessage();
	}
}
