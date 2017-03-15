package org.kite9.diagram.functional.layout;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kite9.diagram.adl.Arrow;
import org.kite9.diagram.adl.Context;
import org.kite9.diagram.adl.Glyph;
import org.kite9.diagram.adl.Link;
import org.kite9.diagram.adl.TextLine;
import org.kite9.diagram.adl.TurnLink;
import org.kite9.diagram.functional.AbstractLayoutFunctionalTest;
import org.kite9.diagram.functional.GraphConstructionTools;
import org.kite9.diagram.position.Direction;
import org.kite9.diagram.position.Layout;
import org.kite9.diagram.visualization.planarization.mgt.builder.DirectedEdgePlanarizationBuilder;
import org.kite9.diagram.xml.DiagramXMLElement;
import org.kite9.diagram.xml.LinkEndStyle;
import org.kite9.diagram.xml.XMLElement;

/**
 * LEMMA: If the user provides a set of directed edges which don't overlap, the
 * {@link DirectedEdgePlanarizationBuilder} will always provide the optimal
 * arrangement of the edges.
 * 
 * @author robmoffat
 *
 */
public class Test22DirectedEdgeInsertion extends AbstractLayoutFunctionalTest {

	@Test
	public void test_22_1_UnconnectedWindows() throws Exception {
		List<XMLElement> glyphs = new ArrayList<XMLElement>();
		GraphConstructionTools.createGrid("a", 2, 2, glyphs, true);
		GraphConstructionTools.createGrid("b", 2, 2, glyphs, true);

		DiagramXMLElement d = new DiagramXMLElement("D", glyphs, null);

		renderDiagram(d);

	}

	@Test
	public void test_22_2_NextToWindows() throws Exception {
		List<XMLElement> glyphs = new ArrayList<XMLElement>();
		Glyph[][] a = GraphConstructionTools.createGrid("a", 2, 2, glyphs, true);
		Glyph[][] b = GraphConstructionTools.createGrid("b", 2, 2, glyphs, true);

		new Link(a[1][1], b[0][0], null, null, null, null, Direction.RIGHT);

		DiagramXMLElement d = new DiagramXMLElement("D", glyphs, null);

		renderDiagram(d);

	}

	@Test
	public void test_22_3_EmbeddedToWindows() throws Exception {
		List<XMLElement> glyphs = new ArrayList<XMLElement>();
		Glyph[][] a = GraphConstructionTools.createGrid("a", 2, 2, glyphs, true);
		Glyph[][] b = GraphConstructionTools.createGrid("b", 2, 2, glyphs, true);

		new Link(a[0][0], b[0][0], null, null, null, null, Direction.RIGHT);
		new Link(b[1][1], a[1][1], null, null, null, null, Direction.RIGHT);

		DiagramXMLElement d = new DiagramXMLElement("D", glyphs, null);

		renderDiagram(d);

	}

	@Test
	public void test_22_4_javaArchitecture2Broken() throws Exception {
		Glyph diagram = new Glyph("xml", "A Diagram Definition", null, null);
		Glyph response = new Glyph("zip file", "Response", createList(new TextLine("PNG Image (or) "), new TextLine("PDF Image"), new TextLine("Client-side image map")), null);

		Arrow sends = new Arrow("sends");
		Arrow receives = new Arrow("receives");

		Glyph objects = new Glyph("java objects", "ADL Model", null, null);
		Glyph xstream = new Glyph("", "XStream", null, null);
		Arrow converts = new Arrow("converts");

		Glyph client = new Glyph("client side", "Kite9 Java Tool", createList(new TextLine("(or another tool)")), null);

		Glyph diagramServer = new Glyph("web-app", "Kite9 Diagram Server", null, null);

		Context transport = new Context("Over the wire", createList(diagram, sends, receives, response), true, new TextLine("HTTP Over Internet"), Layout.DOWN);
		Context yourside = new Context("yours", createList(converts, client, xstream, objects), true, new TextLine("Your Server / PC"), null);
		Context ourside = new Context("ours", createList(diagramServer), true, new TextLine("Kite9 Servers"), null);

		DiagramXMLElement d = new DiagramXMLElement("Arch", createList(yourside, transport, ourside), null);

		// converts

		new Link(converts, client);
		new Link(converts, xstream, null, null, null, new TextLine("using"), null);
		new Link(converts, objects);

		// sends
		new Link(client, sends, null, null, null, null, Direction.RIGHT);
		new Link(sends, diagramServer, null, null, LinkEndStyle.ARROW, null, Direction.RIGHT);
		new Link(sends, diagram);

		// receives
		new Link(diagramServer, receives, null, null, null, null, Direction.LEFT);
		new Link(receives, client, null, null, LinkEndStyle.ARROW, null, Direction.LEFT);
		new Link(receives, response);

		renderDiagram(d);
	}

	@Test
	public void test_22_5_MultipleEdgeInsertion1() throws Exception {
		Glyph a = new Glyph("a", "", "a", null, null);
		Glyph b = new Glyph("b", "", "b", null, null);

		DiagramXMLElement d = new DiagramXMLElement("Arch", listOf(a, b), null);

		new TurnLink(a, b, null, null, null, null, Direction.DOWN);
		new TurnLink(a, b, null, null, null, null, null);
		new TurnLink(a, b, null, null, null, null, null);
		new TurnLink(a, b, null, null, null, null, null);
		new TurnLink(a, b, null, null, null, null, null);
		new TurnLink(a, b, null, null, null, null, null);

		renderDiagram(d);
	}

	@Test
	public void test_22_6_MultipleEdgeInsertion2() throws Exception {
		Glyph a = new Glyph("a", "", "a", null, null);
		Glyph b = new Glyph("b", "", "b", null, null);
		Glyph c = new Glyph("c", "", "c", null, null);

		DiagramXMLElement d = new DiagramXMLElement("Arch", listOf(a, b, c), null);

		new TurnLink(a, b, null, null, null, null, Direction.DOWN);
		new TurnLink(a, c, null, null, null, null, Direction.DOWN);
		new TurnLink(a, b, null, null, null, null, null);
		new TurnLink(a, c, null, null, null, null, null);
		new TurnLink(a, b, null, null, null, null, null);
		new TurnLink(c, b, null, null, null, null, Direction.UP);

		renderDiagram(d);
	}

}