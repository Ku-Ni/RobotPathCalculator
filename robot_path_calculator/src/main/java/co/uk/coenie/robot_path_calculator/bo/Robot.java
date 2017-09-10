package co.uk.coenie.robot_path_calculator.bo;

import java.util.HashSet;
import java.util.Set;

import co.uk.coenie.robot_path_calculator.dto.Coord;

public class Robot {

	private Coord location;
	private Set<Coord> spotsCleaned;
	
	public Robot(Coord startPosition) {
		spotsCleaned = new HashSet<>();
		location = startPosition;
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
		location = new Coord(location.getX(), location.getY()+1);
	}
	
	public void moveEast() {
		location = new Coord(location.getX()+1, location.getY());
	}
	
	public void moveSouth() {
		location = new Coord(location.getX(), location.getY() - 1);
	}
	
	public void moveWest() {
		location = new Coord(location.getX()-1, location.getY());
	}

	public int getX() {
		return location.getX();
	}

	public int getY() {
		return location.getY();
	}

}
