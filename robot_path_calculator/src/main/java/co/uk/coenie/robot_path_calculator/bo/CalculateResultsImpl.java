package co.uk.coenie.robot_path_calculator.bo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import co.uk.coenie.robot_path_calculator.dto.Request;
import co.uk.coenie.robot_path_calculator.dto.Response;
import co.uk.coenie.robot_path_calculator.dto.SuccessfulResponse;

@Component
public class CalculateResultsImpl implements CalculateResults{
	private Logger logger = LogManager.getLogger(CalculateResultsImpl.class);

	@Override
	public Response processInput(Request request) throws IllegalArgumentException {
		Robot robot = new Robot(request.getStartPosition(), request.getRoomSize());

		// Handle instance where start location is on a patch
		if (request.getPatches().contains(robot.getLocation()))
			robot.cleanSpot();

		// Loops over the instructions and moves robot
		// validate coordinates and clean patches.
		for (char c: request.getInstructions()) {
			switch (c) {
			case 'N':
				robot.moveNorth();
				break;
			case 'E':
				robot.moveEast();
				break;
			case 'S':
				robot.moveSouth();
				break;
			case 'W':
				robot.moveWest();
				break;
			default:
				throw new IllegalArgumentException(c + " not valid direction");
			}
			
			logger.debug("Robot location: {}", robot.getLocation());
						
			// Check if robot is on patch
			if (request.getPatches().contains(robot.getLocation())) {
				logger.debug("Patches {} contains {}", request.getPatches(), robot.getLocation());
				robot.cleanSpot();
			}
			
		}

		return new SuccessfulResponse(robot.getLocation(), robot.getSpotsCleaned().size());
	}
}
