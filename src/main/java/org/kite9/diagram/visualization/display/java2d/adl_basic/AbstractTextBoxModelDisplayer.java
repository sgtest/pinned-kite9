package org.kite9.diagram.visualization.display.java2d.adl_basic;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.kite9.diagram.adl.Symbol;
import org.kite9.diagram.adl.TextLine;
import org.kite9.diagram.position.CostedDimension;
import org.kite9.diagram.position.Dimension2D;
import org.kite9.diagram.position.RectangleRenderingInformation;
import org.kite9.diagram.primitives.Connection;
import org.kite9.diagram.primitives.DiagramElement;
import org.kite9.diagram.primitives.StyledText;
import org.kite9.diagram.visualization.display.CompleteDisplayer;
import org.kite9.diagram.visualization.display.java2d.style.Stylesheet;
import org.kite9.diagram.visualization.display.java2d.style.TextBoxStyle;
import org.kite9.diagram.visualization.display.java2d.style.TextStyle;

/**
 * Handles rendering and sizing of text objects within the diagram. 
 * 
 * Pieces of text generally have a label, symbols and a type.  Any of those pieces are optional.
 * 
 * These can be used as labels for containers or
 * connections, so there are various subclasses for those.
 * 
 * 
 * @author robmoffat
 * 
 */
public abstract class AbstractTextBoxModelDisplayer extends AbstractBoxModelDisplayer {

	public AbstractTextBoxModelDisplayer(Graphics2D g2, Stylesheet ss) {
		super(g2, ss);
	}

	public AbstractTextBoxModelDisplayer(CompleteDisplayer parent, Graphics2D g2, Stylesheet ss, boolean shadow, int xo, int yo) {
		super(parent, g2, ss, shadow, xo, yo);
	}

	@Override
	protected void drawBoxContents(DiagramElement element, RectangleRenderingInformation r) {
		Rectangle2D ri = getDrawingRectangle(element, r);
//		g2.setColor(Color.GREEN);
//		g2.draw(ri);
		List<Symbol> symbols = getSymbols(element);
		String stereo = safeGetText(getStereotype(element));
		String label = safeGetText(getLabel(element));
		boolean syms = (symbols != null) && (symbols.size() > 0);
		double xStart = ri.getMinX();
		double yStart = ri.getMinY();
		TextStyle symStyle = ss.getSymbolTextStyle();
		double symWidth = syms ? getSymbolWidth() : 0;
		CostedDimension cd = CostedDimension.ZERO;
		
		// draw stereotype, and symbols next to it if there are any
		TextStyle ts = getTypeStyle(element);
		if (ts != null) {
			double baseline = Math.max(getBaseline(ts.getFont(), g2, stereo), syms ? getSymbolBaseline(ts.getFont(), symStyle.getFont(), g2) : 0);
			cd = arrangeString(ts.getFont(), ts.getColor(), stereo, new Dimension2D(xStart, yStart), new Dimension2D(ri.getWidth(), ri.getHeight()), true, syms ? Justification.LEFT : ts.getJust(), baseline);
			if (syms && (!cd.equals(CostedDimension.ZERO))) {
				drawSymbols(symbols, xStart, yStart, ri.getWidth(), baseline);
				yStart += Math.max(cd.getHeight(), symWidth);
				syms = false;
			} else {
				yStart += cd.getHeight();
			}
		}
		// draw label, and syms if they haven't already been done
		TextStyle ls = getLabelStyle(element);
		double baseline2 = Math.max(getBaseline(ls.getFont(), g2, label), syms ? getSymbolBaseline(ls.getFont(), symStyle.getFont(), g2) : 0);
//		g2.setColor(Color.RED);
//		g2.drawRect((int) xStart, (int) (yStart+baseline), 100, 1);
		cd = arrangeString(ls.getFont(), ls.getColor(), label, new Dimension2D(xStart, yStart), new Dimension2D(ri.getWidth(), ri.getHeight()), true, syms ? Justification.LEFT : ls.getJust(), baseline2);
		if (syms) {
			drawSymbols(symbols, xStart, yStart, ri.getWidth(), baseline2);
			yStart += Math.max(cd.getHeight(), symWidth) ;
		} else {
			yStart += cd.getHeight();
		}
	}
	
	private void drawSymbols(List<Symbol> syms, double x, double y, double w, double baseline) {
		for (int i = 0; i < syms.size(); i++) {
			Symbol sym = syms.get(i);
			drawSymbol(""+sym.getChar(), g2, x + w - ((i+1) * getSymbolWidth()), y, ss.getSymbolShapes().get(sym.getShape().name()), ss.getSymbolTextStyle().getFont(), baseline);
		}
	}
	
	public abstract StyledText getLabel(DiagramElement de);
	
	public abstract List<Symbol> getSymbols(DiagramElement de);
	
	public abstract StyledText getStereotype(DiagramElement de);
	
	public boolean hasContent(DiagramElement de) {
		List<Symbol> symbols = getSymbols(de);
		String stereo = safeGetText(getStereotype(de));
		boolean syms = (symbols != null) && (symbols.size() > 0);
		String label = safeGetText(getLabel(de));
		boolean lab = (label != null) && (label.length() > 0);
		boolean ster = (stereo != null) && (stereo.length() > 0);
		return syms || lab || ster;
	}
	
	@Override
	public TextBoxStyle getBoxStyle(DiagramElement de) {
		return (TextBoxStyle) super.getBoxStyle(de);
	}
	
	public TextStyle getLabelStyle(DiagramElement de) {
		TextStyle ts = getBoxStyle(de).getLabelTextFormat();
		StyledText st = getLabel(de);
			
		if ((st !=null) && (st.getStyle()!= null)) {
			ts = TextStyle.override(st.getStyle(), ts);
		}
		
		return ts;
	}
	
	public TextStyle getTypeStyle(DiagramElement de) {
		TextStyle ts = getBoxStyle(de).getTypeTextFormat();
		StyledText st = getStereotype(de);
			
		if ((st != null) && (st.getStyle()!= null)) {
			ts = TextStyle.override(st.getStyle(), ts);
		}
		
		return ts;
	}
	
	
	public boolean canDisplay(DiagramElement element) {
		return ((element instanceof TextLine) && ((TextLine)element).getParent() instanceof Connection);
	}

	@Override
	protected Dimension2D sizeBoxContents(DiagramElement e, Dimension2D within) {		
		//if (hasContent(e)) {
			TextStyle ts = getTypeStyle(e);
			TextStyle ls = getLabelStyle(e);
			
			double symbolWidth = getSymbolWidth();
			CostedDimension typeSize = ts != null ? arrangeString(ts.getFont(), ts.getColor(), safeGetText(getStereotype(e)), 
					CostedDimension.ZERO, CostedDimension.UNBOUNDED, false, Justification.CENTER, 0) : CostedDimension.ZERO;
			CostedDimension nameSize = ls != null ? arrangeString(ls.getFont(), ls.getColor(), safeGetText(getLabel(e)), 
					CostedDimension.ZERO, CostedDimension.UNBOUNDED, false, Justification.CENTER, 0) : CostedDimension.ZERO;
			
			int syms = getSymbols(e)==null ? 0 : getSymbols(e).size();
			if (syms > 0) {
				CostedDimension symd = new CostedDimension(ss.getInterSymbolPadding() + (syms * symbolWidth), symbolWidth, 0);
				if (!typeSize.equals(CostedDimension.ZERO)) {
					FontMetrics fm = g2.getFontMetrics(ts.getFont());
					double descent = fm.getMaxDescent();
					typeSize = arrangeHorizontally(arrangeVertically(symd, new Dimension2D(0, descent)), typeSize);
				} else {
					FontMetrics fm = g2.getFontMetrics(ls.getFont());
					double descent = fm.getMaxDescent();
					nameSize = arrangeHorizontally(arrangeVertically(symd, new Dimension2D(0, descent)), nameSize);
				}
			
			}
			 
			if ((typeSize == CostedDimension.NOT_DISPLAYABLE) || (nameSize == CostedDimension.NOT_DISPLAYABLE))
				return CostedDimension.NOT_DISPLAYABLE;
			
			CostedDimension fullSize = arrangeVertically(typeSize, nameSize);
			return fullSize;
//		} else {
//			return CostedDimension.ZERO;
//		}
	}

	private String safeGetText(StyledText st) {
		return st == null ? null : st.getText();
	}
	
	
	
}
