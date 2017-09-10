/**
 * 
 */
package co.uk.coenie.robot_path_calculator.bo;

import co.uk.coenie.robot_path_calculator.dto.Request;
import co.uk.coenie.robot_path_calculator.dto.Response;

/**
 * @author Coenie
 *
 */
public interface CalculateResults {

	/**
	 * Takes input data and initiates Robot with start location. Move robot
	 * from as instructed and cleans patches.
	 * 
	 * @param request
	 * @return response - Final coordinate of robot and number of patches cleaned
	 * @throws IllegalArgumentException if robot is moved outside of room
	 */
	public Response processInput(Request input) throws IllegalArgumentException;

}
