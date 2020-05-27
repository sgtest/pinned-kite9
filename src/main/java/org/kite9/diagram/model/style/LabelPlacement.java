package org.kite9.diagram.model.style;

import org.kite9.diagram.model.position.Direction;

public enum LabelPlacement {

	TOP, LEFT, BOTTOM, RIGHT, TOP_LEFT, BOTTOM_LEFT, TOP_RIGHT, BOTTOM_RIGHT;

	public boolean containerLabelPlacement(Direction d) {
		switch (this) {
		case TOP:
		case TOP_LEFT:
		case TOP_RIGHT:
			return d == Direction.UP;
		case LEFT:
			return d == Direction.LEFT;
		case RIGHT:
			return d == Direction.RIGHT;
		case BOTTOM_LEFT:
		case BOTTOM_RIGHT:
		case BOTTOM:
		default:
			return d == Direction.DOWN;
		}
	}
	
	public boolean sameAxis(Direction d) {
		switch (this) {
		case TOP:
		case TOP_LEFT:
		case TOP_RIGHT:
		case BOTTOM_LEFT:
		case BOTTOM_RIGHT:
		case BOTTOM:
		default:
			return Direction.isHorizontal(d);
		case LEFT:
		case RIGHT:
			return Direction.isVertical(d);
		}
	
	}

	public Direction connectionLabelPlacementDirection(Direction d) {
		if ((d == Direction.UP) || (d ==Direction.DOWN)) {
			switch (this) {
			case LEFT:
			case TOP_LEFT:
			case BOTTOM_LEFT:
				return Direction.RIGHT;
			default:
			case TOP:
			case BOTTOM:
			case BOTTOM_RIGHT:
			case TOP_RIGHT:
			case RIGHT:
				return Direction.LEFT;
			}
		} else {
			switch (this) {
			default:
			case RIGHT:
			case BOTTOM:
			case LEFT:
			case BOTTOM_LEFT:
				return Direction.UP;
			case BOTTOM_RIGHT:
			case TOP:
			case TOP_LEFT:
			case TOP_RIGHT:
				return Direction.DOWN;
			}			
		}
	}

	Object isVertical() {
		// TODO Auto-generated method stub
		return null;
	}
}