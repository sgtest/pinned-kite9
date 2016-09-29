package org.kite9.diagram.visualization.planarization.grid;

import org.apache.commons.math.fraction.BigFraction;
import org.kite9.diagram.adl.Container;
import org.kite9.diagram.adl.DiagramElement;
import org.kite9.diagram.common.objects.Pair;
import org.kite9.diagram.common.objects.OPair;
import org.kite9.diagram.position.Layout;

/**
 * Handles positioning of elements for {@link Layout}.GRID.  
 * 
 * 
 * @author robmoffat
 *
 */
public interface GridPositioner {

	/**
	 * Works out the position of the elements within the grid for a given container.
	 * 
	 * Should cache too.
	 */
	public DiagramElement[][] placeOnGrid(Container ord);
	
	/**
	 * Returns the XPosition as a pair of fractions, showing where a given element has been 
	 * placed within the grid.
	 */
	public OPair<BigFraction> getGridXPosition(DiagramElement elem);
	
	/**
	 * Returns the XPosition as a pair of fractions, showing where a given element has been 
	 * placed within the grid.
	 */
	public OPair<BigFraction> getGridYPosition(DiagramElement elem);
}