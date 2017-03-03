package org.kite9.diagram.visualization.display;

import java.util.ArrayList;
import java.util.List;

import org.kite9.diagram.adl.Connected;
import org.kite9.diagram.adl.Connection;
import org.kite9.diagram.adl.Container;
import org.kite9.diagram.adl.Diagram;
import org.kite9.diagram.adl.DiagramElement;
import org.kite9.diagram.adl.Terminator;
import org.kite9.diagram.adl.Text;
import org.kite9.diagram.position.CostedDimension;
import org.kite9.diagram.position.Dimension2D;
import org.kite9.diagram.position.Direction;
import org.kite9.diagram.position.RectangleRenderingInformation;
import org.kite9.diagram.visualization.display.components.AbstractRouteDisplayer;
import org.kite9.diagram.visualization.display.style.ShapeStyle;
import org.kite9.diagram.visualization.display.style.TerminatorShape;
import org.kite9.diagram.xml.LinkLineStyle;
import org.kite9.framework.logging.Kite9Log;
import org.kite9.framework.logging.Logable;

public abstract class AbstractCompleteDisplayer implements CompleteDisplayer, DiagramSizer, Logable {
	
	private int gridSize = 12;

	public static final CompleteDisplayer NULL = new NullDisplayer();

	protected List<ComponentDisplayer> displayers = new ArrayList<ComponentDisplayer>();
	double buffer;
	protected Kite9Log log = new Kite9Log(this);


	/**
	 * Set buffer > 0 to ensure gaps even around invisible attr.  0 should be used for correct rendering
	 */
	public AbstractCompleteDisplayer(boolean buffer, int gridSize) {
		this.buffer = buffer ? gridSize : 0;
		this.gridSize = gridSize;
	}

	public Displayer getDisplayer(DiagramElement de) {
		for (Displayer c : displayers) {
			if (c.canDisplay(de)) {
				return c;
			}
		}
	
		return NULL;
	}

	public boolean isVisibleElement(DiagramElement de) {
		Displayer ded = getDisplayer(de);
		if (ded == null) {
			return false;
		} else if (de.getRenderingInformation().isRendered()) {
			return ded.isVisibleElement(de);			
//		} else if (de instanceof CompositionalDiagramElement) {
//			return ded.isVisibleElement(de);
		} else {
			return false;
		}
	}

	public boolean canDisplay(DiagramElement element) {
		Displayer d = getDisplayer(element);
		return d.canDisplay(element);
	}
	
	public CostedDimension size(DiagramElement element, Dimension2D within) {
		for (Displayer cd : displayers) {
			if (cd.canDisplay(element)) {
				return cd.size(element, within);
			}
		}
		
		return NULL.size(element, within);
	}
	

	
	
	public static final double MINIMUM_GLYPH_SIZE = 2;
	public static final double MINIMUM_ARROW_SIZE = 2;
	
	public static final double EDGE_DISTANCE = 2;
	

	public double getMinimumDistanceBetween(DiagramElement a, Direction aSide, DiagramElement b, Direction bSide, Direction d) {
		double distance = getMinimumDistanceInner(a,aSide,  b, bSide, d, true);
		log.send(log.go() ? null : "Minimum distances between  " + a + "  " + aSide+ " "+ b + " "+ bSide +" in " + d + " is " + distance);
		return distance;

	}

	/**
	 * Given two diagram attr, separated with a gutter, figure out how much
	 * space must be between them.
	 */
	private double getMinimumDistanceInner(DiagramElement a, Direction aSide, DiagramElement b, Direction bSide, Direction d, boolean reverse) {
		// this part deals with internal distances
		if (a == b) {
			return getInternalDistance(a, aSide, bSide); 
		}

		if (!needsDistance(a, b)) {
			return buffer;
		}
		
		// distances when one element is contained within another
		if ((a instanceof Container) && (((Container) a).getContents().contains(b))) {
			return getPadding(a, d);
		} else if ((b instanceof Container) && (((Container) b).getContents().contains(a))) {
			return getPadding(b, Direction.reverse(d));
		}
		
		Direction dd = Direction.reverse(d);
		double marginA = (aSide == d) ? 
				getMargin(a, dd) 
				: 0;
		double marginB = (bSide == dd) ? 
				getMargin(b, d) 
				: 0;
				
		double margin = Math.max(marginA, marginB);
		
		if (a instanceof Connected) {
			if (b instanceof Connection) {
				if (((Connection) b).meets((Connected) a)) {
					if (needsDistance(a)) {
						return margin;
					} else {
						return 0;
					}
				} else {
					return margin;
				}
			} 
			
			return margin;
		} 

		if (a instanceof Connection) {
			if ((b instanceof Connection) || (b==null)) {
				return margin;
			} 
		}

		if (reverse) {
			return getMinimumDistanceInner(b, bSide,  a, aSide, Direction.reverse(d), false);
		} else {
			return 0;
		}
	}

	private double getInternalDistance(DiagramElement a, Direction aSide, Direction bSide) {
		if (a == null) {
			return 0;
		} else if ((aSide == null) && (bSide == null)) {
			// two elements within a container grid
			return 0;

		} else if (bSide == null) {
			return getDisplayer(a).getPadding(a, aSide);
		} else if (aSide == null) {
			return getDisplayer(a).getPadding(a, bSide);
		} else if ((aSide == Direction.LEFT) || (aSide == Direction.RIGHT)) {
			return getDisplayer(a).size(a, CostedDimension.UNBOUNDED).getWidth();
		} else {
			return getDisplayer(a).size(a, CostedDimension.UNBOUNDED).getHeight();
		}

	}

	public double getLinkMargin(DiagramElement a, Direction d) {
		return getDisplayer(a).getLinkMargin(a, d);
	}
	
	@Override
	public double getLinkGutter(DiagramElement element, Direction d) {
		return EDGE_DISTANCE * gridSize;
	}

	private boolean needsDistance(DiagramElement a, DiagramElement b) {
		return needsDistance(a) && needsDistance(b);
	}
	
	private boolean needsDistance(DiagramElement d) {
		if (d==null) {
			return false;
		} else if (d instanceof Container) {
			return ((Container)d).isBordered();
		} else if (d instanceof Connection){
			Connection l = (Connection) d;
			if (l.getStyle()==LinkLineStyle.INVISIBLE) {
				return false;
			} else {
				return true;
			}
		} else if (d instanceof Diagram) {
			return true;
		} else {
			boolean rd = requiresDimension(d);
			if (!rd) {
				if (d.getRenderingInformation() instanceof RectangleRenderingInformation) {
					RectangleRenderingInformation rri =(RectangleRenderingInformation) d.getRenderingInformation();
					return rri.isMultipleHorizontalLinks() || rri.isMultipleVerticalLinks();
				}
			}
		}
		
		return true;
	}

	public String getPrefix() {
		return "DD  ";
	}

	public boolean isLoggingEnabled() {
		return true;
	}

	@Override
	public double getPadding(DiagramElement element, Direction d) {
		return getDisplayer(element).getPadding(element, d);
	}
	
	public abstract double getMargin(DiagramElement element, Direction d);
	
	

	@Override
	public boolean requiresDimension(DiagramElement de) {
		if (buffer > 0) {
			return true;
		} else {
			return getDisplayer(de).requiresDimension(de);
		}
	}

	@Override
	public double getTerminatorLength(Terminator terminator) {
		if (terminator != null) {
			TerminatorShape fs = new TerminatorShape(terminator);
			return fs.getMinInputLinkLength();
		} else {
			return 0;
		}
	}
	
	@Override
	public double getTerminatorReserved(Terminator terminator, Connection on) {
		if (terminator != null) {
			TerminatorShape fs = new TerminatorShape(terminator);
			ShapeStyle ss = ((AbstractRouteDisplayer) getDisplayer(on)).getStyle(on);
			double width = ss.getStrokeWidth();
			return fs.getReservedLength(width);
		} else {
			return 0;
		}
	}
	
	

}