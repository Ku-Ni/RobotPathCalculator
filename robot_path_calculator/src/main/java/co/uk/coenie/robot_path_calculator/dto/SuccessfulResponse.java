package co.uk.coenie.robot_path_calculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessfulResponse implements Response {
	
	@JsonProperty("coords")
	private Coord coords;	
	
	@JsonProperty("patches")
	private int patches;
	
	
	public SuccessfulResponse () {}
	
	
	public SuccessfulResponse (Coord coord, int patches) {
		this.coords = coord;
		this.patches = patches;
	}
}
