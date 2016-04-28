package org.kite9.diagram.visualization.compaction;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.kite9.diagram.common.elements.Edge;
import org.kite9.diagram.common.elements.PositionAction;
import org.kite9.diagram.common.elements.Vertex;
import org.kite9.diagram.position.Direction;
import org.kite9.diagram.primitives.DiagramElement;
import org.kite9.diagram.visualization.orthogonalization.Dart;


/**
 * A segment is a set of vertices that must have the same horizontal or vertical position.
 * 
 * 
 * @author robmoffat
 *
 */
public class Segment implements Comparable<Segment> {
	
	public PositionAction dimension;
	public int i;
	public boolean positioned = false;
	public double position;
	public DiagramElement underlying;
	public DiagramElement getUnderlying() {
		return underlying;
	}

	public void setUnderlying(DiagramElement underlying) {
		this.underlying = underlying;
	}

	public Direction getUnderlyingSide() {
		return underlyingSide;
	}

	public void setUnderlyingSide(Direction underlyingSide) {
		this.underlyingSide = underlyingSide;
	}

	public Direction underlyingSide;
	
	public Segment(PositionAction dimension, int i) {
		this.dimension = dimension;
		this.i = i;
	}
	
	public void addToSegment(Vertex v) {
		verticesInSegment.add(v);
	}

	public String getIdentifier() {
		return dimension+" ("+i+", "+underlying+", "+underlyingSide+")";
	}
	
	@Override
	public String toString() {
		 return getIdentifier() + " pos: "+position+"  "+verticesInSegment.toString();
	}

	public void setPosition(double d) {
		for (Vertex v : getVerticesInSegment()) {
			dimension.set(v, d);
		}
	
		position = d;
	}
	
	private Set<Vertex> verticesInSegment = new LinkedHashSet<Vertex>();

	public Set<Vertex> getVerticesInSegment() {
		return verticesInSegment;
	}
	
	public boolean connects(Vertex a, Vertex b) {
		return (inSegment(a) && inSegment(b));
	}

	private boolean inSegment(Vertex b) {
		return verticesInSegment.contains(b);
	}

	public boolean isPositioned() {
		return positioned;
	}

	public void setPositioned(boolean positioned) {
		this.positioned = positioned;
	}

	public double getPosition() {
		return position;
	}

	public PositionAction getDimension() {
		return dimension;
	}

	/**
	 * De-facto ordering for segments.
	 */
	public int compareTo(Segment o) {
		Double pos = this.position;
		return pos.compareTo(o.position);
		
		// TODO: need to incorporate dependencies here too, when values are equal
	}
	
	/**
	 * This is a utility method, used to set the positions of the darts for the diagram
	 */
	public Collection<Dart> getDartsInSegment() {
		Collection<Dart> darts = new HashSet<Dart>();
		for (Vertex v : verticesInSegment) {
			for (Edge e : v.getEdges()) {
				if (e instanceof Dart) { 
					if (dimension==PositionAction.YAction) {
						if ((e.getDrawDirection()==Direction.LEFT) || (e.getDrawDirection()==Direction.RIGHT)) {
							darts.add((Dart)e);
						}
					} else if (dimension==PositionAction.XAction) {
						if ((e.getDrawDirection()==Direction.UP) || (e.getDrawDirection()==Direction.DOWN)) {
							darts.add((Dart)e);
						}
					}
				}
			}
		}
		
		return darts;
	}
}