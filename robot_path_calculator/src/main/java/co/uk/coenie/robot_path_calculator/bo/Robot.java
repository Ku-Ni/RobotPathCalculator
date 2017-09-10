package co.uk.coenie.robot_path_calculator.bo;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.uk.coenie.robot_path_calculator.dto.Coord;

public class Robot {
	private Logger logger = LogManager.getLogger(Robot.class);

	private Coord location;
	private int maxX;
	private int maxY;
	private Set<Coord> spotsCleaned;

	public Robot(Coord startPosition, Coord roomSize) {
		spotsCleaned = new HashSet<>();
		location = startPosition;
		maxX = roomSize.getX()-1;			// Coordinates start at 0, max is roomSize - 1
		maxY = roomSize.getY()-1;			// Coordinates start at 0, max is roomSize - 1
	}

	public Coord getLocation() {
		return location;
	}

	public void cleanSpot() {
		spotsCleaned.add(location);
	}

	public Set<Coord> getSpotsCleaned(){
		return spotsCleaned;
	}

	public void moveNorth() {
		if (location.getY() < maxY)
			location = new Coord(location.getX(), location.getY()+1);
		else
			logger.info("Robot hitting North wall");
	}

	public void moveEast() {
		if (location.getX() < maxX)
			location = new Coord(location.getX()+1, location.getY());
		else
			logger.info("Robot hitting East wall");
	}

	public void moveSouth() {
		if (location.getY() > 0)
			location = new Coord(location.getX(), location.getY() - 1);
		else
			logger.info("Robot hitting South wall");
	}

	public void moveWest() {
		if (location.getX() > 0)
			location = new Coord(location.getX()-1, location.getY());
		else
			logger.info("Robot hitting West wall");
	}

	public int getX() {
		return location.getX();
	}

	public int getY() {
		return location.getY();
	}

}
