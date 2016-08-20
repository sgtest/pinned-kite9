package org.kite9.diagram.visualization.display.components;

import java.util.Collections;
import java.util.List;

import org.kite9.diagram.adl.CompositionalDiagramElement;
import org.kite9.diagram.adl.ContainerProperty;
import org.kite9.diagram.adl.DiagramElement;
import org.kite9.diagram.adl.Glyph;
import org.kite9.diagram.adl.StyledDiagramElement;
import org.kite9.diagram.adl.Symbol;
import org.kite9.diagram.adl.Text;
import org.kite9.diagram.visualization.display.CompleteDisplayer;
import org.kite9.diagram.visualization.display.style.BoxStyle;
import org.kite9.diagram.visualization.display.style.FlexibleShape;
import org.kite9.diagram.visualization.format.GraphicsLayer;


public class GlyphDisplayer extends AbstractTextWithContentBoxModelDisplayer {
	
	AbstractBoxModelDisplayer lineDisplayer; 
	

	public GlyphDisplayer(CompleteDisplayer parent, GraphicsLayer g2, boolean shadow) {
		super(parent, g2, shadow);
	}

	
	
	public boolean canDisplay(DiagramElement element) {
		return element instanceof Glyph;
	}

	@Override
	public Text getLabel(DiagramElement de) {
		return ((Glyph)de).getLabel();
	}

	@Override
	public ContainerProperty<Symbol> getSymbols(DiagramElement de) {
		return ((Glyph)de).getSymbols();
	}

	@Override
	public Text getStereotype(DiagramElement de) {
		return ((Glyph)de).getStereotype();
	}

	@Override
	public BoxStyle getUnderlyingStyle(DiagramElement de) {
		return new BoxStyle((StyledDiagramElement)de);
	}

	/**
	 * Adds a divider if necessary
	 */
	@Override
	public List<CompositionalDiagramElement> getContents(DiagramElement de) {
		ContainerProperty<CompositionalDiagramElement> out =  ((Glyph)de).getText();

		if ((out == null) || (out.size() == 0)) {
			return Collections.emptyList();
		} else {
			return out.asList();
		}
	}

	@Override
	protected boolean applyBoxContentCentering() {
		return true;
	}

	@Override
	protected FlexibleShape getDefaultBorderShape(DiagramElement de) {
		return AbstractBoxModelDisplayer.DEFAULT_SHAPE;
	}
}
