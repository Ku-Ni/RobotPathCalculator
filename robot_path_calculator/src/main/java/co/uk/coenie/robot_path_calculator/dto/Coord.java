package co.uk.coenie.robot_path_calculator.dto;

import java.util.AbstractList;
import java.util.Objects;

public class Coord extends AbstractList<Integer>{

	private final Integer[] coords;

	public Coord() {
		coords = new Integer[2];
	}

	public Coord(int x, int y) {
		this();
		coords[0] = x;
		coords[1] = y;
	}


	public Integer getX() {
		return coords[0];
	}

	public Integer getY() {
		return coords[1];
	}

	@Override
	public Integer get(int index) {
		if (index == 0 || index == 1)
			return coords[index];

		throw new IndexOutOfBoundsException("Coord only has 2 indexes");
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null ||
				!(o instanceof Coord))
			return false;

		if (o == this)
			return true;

		Coord coord = (Coord) o;
		return Objects.equals(coords[0], coord.coords[0]) &&
				Objects.equals(coords[1], coord.coords[1]) ;
	}


	@Override
	public int hashCode() {
		return Objects.hash(coords[0], coords[1]);
	}


	/**
	 * Used by Jackson databind when mapping Json to Objects
	 * 
	 * @throws IllegalArgumentException - when adding more than two items
	 */
	@Override
	public boolean add(Integer i) throws IllegalArgumentException{
		if (coords[0] == null) 
			coords[0] = i;
		else if (coords[1] == null)
			coords[1] = i;
		else
			throw new IllegalArgumentException("Coord can only have two items");

		return true;

	}
}
