package org.kite9.diagram.common.elements.vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.kite9.diagram.common.fraction.BigFraction;
import org.kite9.diagram.model.DiagramElement;
import org.kite9.diagram.model.position.Direction;
import org.kite9.diagram.model.position.HPos;
import org.kite9.diagram.model.position.VPos;
import org.kite9.diagram.logging.LogicException;

/**
 * Represents corners of diagrams, containers and any other rectangular content.
 * For elements with grid-layout, these also represent points within the grid that will need to be connected up.
 * Multi-corners can be the corners of multiple different diagram elements.
 */
public class MultiCornerVertex extends AbstractAnchoringVertex implements MultiElementVertex {
	
	public static final boolean isMin(BigFraction b) {
		return b.equals(BigFraction.Companion.getZERO());
	}
	
	public static final boolean isMax(BigFraction b) {
		return b.equals(BigFraction.Companion.getONE());
	}
	
	
	public static BigFraction getOrdForXDirection(Direction d) {
		switch (d) {
		case LEFT:
			return BigFraction.Companion.getZERO();
		case RIGHT:
			return BigFraction.Companion.getONE();
		default:
			return BigFraction.Companion.getONE_HALF();
		}
	}
	
	public static BigFraction getOrdForYDirection(Direction d) {
		switch (d) {
		case UP:
			return BigFraction.Companion.getZERO();
		case DOWN:
			return BigFraction.Companion.getONE();
		default:
			return BigFraction.Companion.getONE_HALF();
		}
	}
	
	@Override
	public boolean hasDimension() {
		return false;
	}

	private BigFraction xOrd, yOrd;
	private List<Anchor> anchors = new ArrayList<AbstractAnchoringVertex.Anchor>(4);
	
	public List<Anchor> getAnchors() {
		return anchors;
	}

	public MultiCornerVertex(String id, BigFraction xOrd, BigFraction yOrd) {
		super(id+"_"+xOrd+"_"+yOrd);
		this.xOrd = xOrd;
		this.yOrd = yOrd;
	}
	
	public BigFraction getXOrdinal() {
		return xOrd;
	}
	
	@Override
	public void setX(double x) {
		super.setX(x);
		for (Anchor anchor : anchors) {
			anchor.setX(x);
		}
	}

	@Override
	public void setY(double y) {
		super.setY(y);
		for (Anchor anchor : anchors) {
			anchor.setY(y);
		}
	}

	public BigFraction getYOrdinal() {
		return yOrd;
	}
	
	public void addAnchor(HPos lr, VPos ud, DiagramElement underlying) {
		anchors.add(new Anchor(ud, lr, underlying));
	}
	
	public VPos getVPosFor(DiagramElement c) {
		for (Anchor anchor : anchors) {
			if (anchor.getDe() == c) {
				return anchor.getUd();
			}
		}
		
		throw new LogicException("No anchor found for container "+c);
	}
	
	public HPos getHPosFor(DiagramElement c) {
		for (Anchor anchor : anchors) {
			if (anchor.getDe() == c) {
				return anchor.getLr();
			}
		}
		
		throw new LogicException("No anchor found for container "+c);
	}
	
	public boolean hasAnchorFor(DiagramElement c) {
		for (Anchor anchor : anchors) {
			if (anchor.getDe() == c) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isPartOf(DiagramElement c) {
		return hasAnchorFor(c);
	}

	@Override
	public Set<DiagramElement> getDiagramElements() {
		return anchors.stream().map(a -> a.getDe()).collect(Collectors.toSet());
	}
}