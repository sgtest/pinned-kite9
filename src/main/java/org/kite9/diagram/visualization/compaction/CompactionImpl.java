package org.kite9.diagram.visualization.compaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kite9.diagram.common.elements.PositionAction;
import org.kite9.diagram.common.elements.Vertex;
import org.kite9.diagram.visualization.orthogonalization.DartFace;
import org.kite9.diagram.visualization.orthogonalization.Orthogonalization;

/**
 * Flyweight class that handles the state of the compaction as it goes along.
 * Contains lots of utility methods too.
 * 
 * 
 * @author robmoffat
 *
 */
public class CompactionImpl implements Compaction {
	
	private Orthogonalization orthogonalization;
	
	public Orthogonalization getOrthogonalization() {
		return orthogonalization;
	}

	public List<Segment> getVerticalSegments() {
		return verticalSegments;
	}

	public List<Segment> getHorizontalSegments() {
		return horizontalSegments;
	}

	List<Segment> verticalSegments;
	List<Segment> horizontalSegments;

	public CompactionImpl(Orthogonalization o, List<Segment> horizontal, List<Segment> vertical, Map<Vertex, Segment> hmap, Map<Vertex, Segment> vmap) {
		this.orthogonalization = o;
		this.horizontalSegments = horizontal;
		this.verticalSegments = vertical;
		this.hMap = hmap;
		this.vMap = vmap;
	}
	
	Map<Vertex, Segment> hMap;

	public Map<Vertex, Segment> getHorizontalVertexSegmentMap() {
		return hMap;
	}

	public Segment[] getFaceSpace(DartFace df) {
		return faceSpaces.get(df);
	}
	
	Map<Vertex, Segment> vMap;

	public Map<Vertex, Segment> getVerticalVertexSegmentMap() {
		return vMap;
	}

	Map<DartFace, Segment[]> faceSpaces = new HashMap<DartFace, Segment[]>();
	
	public void setFaceExtremeSections(DartFace df, Segment[] border) {
		faceSpaces.put(df, border);
	}

	public Vertex createCompactionVertex(Segment s1, Segment s2) {
		Vertex v = orthogonalization.createHelperVertex();
		s1.addToSegment(v);
		s2.addToSegment(v);
		//System.out.println("Added vertex "+v+" to "+s1+" and "+s2);
		setupVertex(v, s1);
		setupVertex(v, s2);
		
		return v;
	}
	
	private void setupVertex(Vertex v, Segment s) {
		if (s.dimension==PositionAction.XAction) {
			vMap.put(v, s);
		} else {
			hMap.put(v, s);
		}
		
	}

	public Segment newSegment(PositionAction direction) {
		Segment snew = null;
		if (direction==PositionAction.XAction) {
			snew = new Segment(direction, getVerticalSegments().size());
			getVerticalSegments().add(snew);
		} else {
			snew = new Segment(direction, getHorizontalSegments().size());
			getHorizontalSegments().add(snew);
		}
		
		return snew;
	}
	
	
}
