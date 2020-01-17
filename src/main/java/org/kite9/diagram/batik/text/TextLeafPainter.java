package org.kite9.diagram.batik.text;

import static org.apache.batik.util.SVGConstants.SVG_G_TAG;
import static org.apache.batik.util.SVGConstants.SVG_NAMESPACE_URI;

import org.apache.batik.gvt.GraphicsNode;
import org.kite9.diagram.batik.bridge.Kite9BridgeContext;
import org.kite9.diagram.batik.painter.SVGLeafPainter;
import org.kite9.diagram.dom.elements.StyledKite9XMLElement;
import org.kite9.diagram.dom.processors.XMLProcessor;
import org.kite9.diagram.model.style.DiagramElementType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Handles painting for {@link DiagramElementType.TEXT} using SVG1.2's flowRoot
 * (supported by Batik, but not implemented in most browsers). 
 * 
 * With the help of {@link LocalRenderingFlowRootElementBridge} we convert this into 
 * regular svg text elements.
 * 
 * @author robmoffat
 *
 */
public class TextLeafPainter extends SVGLeafPainter {
	
	public TextLeafPainter(StyledKite9XMLElement theElement, Kite9BridgeContext ctx) {
		super(theElement, ctx);
	}
	
	@Override
	protected GraphicsNode initGraphicsNode(Element e, Kite9BridgeContext ctx) {
		TextDOMInitializer.setupElementXML((StyledKite9XMLElement) e); 
		return LocalRenderingFlowRootElementBridge.getFlowNode(super.initGraphicsNode(e, ctx));
	}

	@Override
	protected Element processOutput(StyledKite9XMLElement in, Document d, XMLProcessor postProcessor) {
		Element groupElem = d.createElementNS(SVG_NAMESPACE_URI, SVG_G_TAG);
		ExtendedSVGGeneratorContext genCtx = ExtendedSVGGeneratorContext.buildSVGGeneratorContext(d);
		ExtendedSVGGraphics2D g2d = new ExtendedSVGGraphics2D(genCtx, groupElem);
		getGraphicsNode().paint(g2d);
		groupElem = g2d.getTopLevelGroup(true);
		Node firstChild = groupElem.getFirstChild();
		return (Element) firstChild;
	}
	
	
}