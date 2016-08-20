package org.kite9.diagram.visualization.planarization.transform;

import java.util.List;
import java.util.Set;

import org.kite9.diagram.common.algorithms.det.UnorderedSet;
import org.kite9.diagram.common.elements.Edge;
import org.kite9.diagram.common.elements.Vertex;
import org.kite9.diagram.position.Direction;
import org.kite9.diagram.visualization.planarization.EdgeMapping;
import org.kite9.diagram.visualization.planarization.Face;
import org.kite9.diagram.visualization.planarization.Planarization;
import org.kite9.diagram.visualization.planarization.mapping.ContainerVertex;
import org.kite9.diagram.visualization.planarization.mgt.MGTPlanarization;
import org.kite9.diagram.xml.DiagramXMLElement;
import org.kite9.framework.logging.Kite9Log;
import org.kite9.framework.logging.Logable;
import org.kite9.framework.logging.LogicException;

/**
 * Works out which faces are outer faces, and identfies them as such.
 */
public class OuterFaceIdentificationTransform implements PlanarizationTransform, Logable {

	private Kite9Log log = new Kite9Log(this);
	
	@Override
	public void transform(Planarization pln) {
		DiagramXMLElement d = pln.getDiagram();
		EdgeMapping em = pln.getEdgeMappings().get(d);
		Face outerFace = getDiagramOuterFace(em, pln);
		Set<Face> done = new UnorderedSet<Face>(pln.getFaces().size()*2);
		handleOuterFace(outerFace, null, pln, 0, done);
	}
	
	private Face getDiagramOuterFace(EdgeMapping em, Planarization pln) {
		Vertex v = ((MGTPlanarization)pln).getVertexOrder().get(0);
		
		for (Edge e : v.getEdges()) {
			List<Face> faces = pln.getEdgeFaceMap().get(e);
			for (Face face : faces) {
				if ((face.getContainedFaces().size()==0) && isAntiClockwise(v, e)){
					return face;
				}
			}
		}
		
		throw new LogicException("Couldn't find the outer face");
	}
	
	private boolean isAntiClockwise(Vertex v, Edge e) {
		if (v instanceof ContainerVertex) {
			ContainerVertex cv = (ContainerVertex) v;
			if ((cv.getXOrdinal() == 0) && (cv.getYOrdinal() == 0)) {
				return e.getDrawDirectionFrom(v) == Direction.DOWN;
				
				
			}
		}
		
		throw new LogicException("Unexpected Vertex");
	}

	private void handleOuterFace(Face outerFace, Face inside, Planarization pln, int level, Set<Face> done) {
		if (done.contains(outerFace)) {
			return;
		}
		
		done.add(outerFace);
		log.send(level(level)+"Outer face: "+outerFace.getId());
		outerFace.setOuterFace(true);
		outerFace.setContainedBy(inside);
		outerFace.getContainedFaces().clear();
		
		traverseInnerFaces(outerFace, pln, level+1, done);
	}

	private void traverseInnerFaces(Face face, Planarization pln, int level, Set<Face> done) {
		for (int i = 0; i < face.size(); i++) {
			Edge e = face.getBoundary(i);
			List<Face> faces = pln.getEdgeFaceMap().get(e);
			for (Face f : faces) {
				if (f != face) {
					handleInnerFace(f, pln, level, done);
				}
			}
		}
	}


	private String level(int level) {
		StringBuilder sb = new StringBuilder(level);
		for (int i = 0; i < level; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	private void handleInnerFace(Face innerFace, Planarization pln, int level, Set<Face> done) {
		if (done.contains(innerFace)) {
			return;
		}
		
		done.add(innerFace);
		log.send(level(level)+"Inner face: "+innerFace.getId());
		for (Face f : innerFace.getContainedFaces()) {
			handleOuterFace(f, innerFace, pln, level+1, done);
		}
		
		traverseInnerFaces(innerFace, pln, level, done);
	}

	@Override
	public String getPrefix() {
		return "OFIT";
	}

	@Override
	public boolean isLoggingEnabled() {
		return false;
	}
}
