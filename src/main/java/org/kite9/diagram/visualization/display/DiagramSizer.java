package org.kite9.diagram.visualization.display;

import org.kite9.diagram.adl.Connection;
import org.kite9.diagram.adl.DiagramElement;
import org.kite9.diagram.adl.Terminator;
import org.kite9.diagram.position.Direction;

/**
 * Handles spacing between diagram components
 * 
 * @author robmoffat
 *
 */
public interface DiagramSizer {

	/**
	 * Returns the necessary distance between two diagram attr.
	 */
	public double getMinimumDistanceBetween(DiagramElement a, Direction aSide, DiagramElement b, Direction bSide, Direction direction);
	
	/**
	 * Minimum length that an edge section must be to contain this terminator
	 */
	public double getTerminatorLength(Terminator terminator);
	
	/**
	 * Length of edge consumed by the terminator
	 */
	public double getTerminatorReserved(Terminator terminator, Connection on);
	
	/**
	 * Link gutter is the distance between two links arriving at this diagram element
	 */
	public double getLinkGutter(DiagramElement element, Direction d);


}