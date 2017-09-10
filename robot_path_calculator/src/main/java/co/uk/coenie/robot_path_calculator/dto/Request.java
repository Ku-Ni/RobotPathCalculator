package co.uk.coenie.robot_path_calculator.dto;

import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
	@JsonProperty("roomSize")
	private Coord roomSize;
	@JsonProperty("coords")
	private Coord startPosition;
	@JsonProperty("patches")
	private List<Coord> patches;
	@JsonProperty("instructions")
	private String instructions;

	/**
	 * Validates the input data and throws exception when validation fails
	 * 
	 * @throws IllegalArgumentException
	 */
	public void validate() throws IllegalArgumentException {		
		// validate roomSize
		if (roomSize.getX() <= 0 || roomSize.getY() <= 0)
			throw new IllegalArgumentException("Invalid room size "+roomSize+", X and Y must be greater than 0.");

		// Test coords are in room
		if (startPosition.getX() < 0 || startPosition.getX() >= roomSize.getX() ||
				startPosition.getY() < 0 || startPosition.getY() >= roomSize.getY())
			throw new IllegalArgumentException("Invalid start coordinates "+startPosition+", must be in room");

		// Test patches are in room
		for (Coord patch:patches) {
			if (patch.getX() < 0 || patch.getX() >= roomSize.getX() ||
					patch.getY() < 0 || patch.getY() >= roomSize.getY())
				throw new IllegalArgumentException("Invalid patch coordinates "+patch+", must be in room");
		}

		// validate instructions only contain valid directions
		for (char direction:getInstructions()) {
			if (direction!='N' &&
					direction != 'E' &&
					direction != 'S' &&
					direction != 'W')
				throw new IllegalArgumentException("Invalid instruction "+direction+", must be in [N,E,S,W]");
		}
		
		// validate end position in room
		// instructions can still be invalid based on order.
		int yMovement = StringUtils.countOccurrencesOf(instructions, "N") - StringUtils.countOccurrencesOf(instructions, "S");
		int xMovement = StringUtils.countOccurrencesOf(instructions, "E") - StringUtils.countOccurrencesOf(instructions, "W");
		
		if (startPosition.getX()+xMovement < 0 || startPosition.getX()+xMovement >= roomSize.getX() || 
				startPosition.getY()+yMovement < 0 || startPosition.getY()+yMovement >= roomSize.getY())
			throw new IllegalArgumentException("Invalid instructions, final position "+new Coord(startPosition.getX()+xMovement, startPosition.getY()+yMovement)+", must be in room");
	}

	public Coord getRoomSize() {
		return roomSize;
	}

	public Coord getStartPosition() {
		return startPosition;
	}

	public List<Coord> getPatches() {
		return patches;
	}

	public char[] getInstructions() {
		return instructions.toCharArray();
	}	

}
