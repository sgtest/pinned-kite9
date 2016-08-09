package org.kite9.diagram.functional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kite9.diagram.adl.Context;
import org.kite9.diagram.adl.Diagram;
import org.kite9.diagram.adl.Glyph;
import org.kite9.diagram.adl.Link;
import org.kite9.diagram.adl.XMLElement;
import org.kite9.diagram.position.Direction;
import org.kite9.diagram.position.Layout;

public class Test30VertexOrderingContained extends AbstractFunctionalTest {

	@Test
	public void test_30_1_BigSquareDirected() throws IOException {
		List<XMLElement> contents = new ArrayList<XMLElement>();
		Glyph[][] out = GraphConstructionTools.createXContainers("g", 4, 4, contents, Layout.HORIZONTAL);

		Context top = new Context("top", listOf(contents.get(0), contents.get(1)), true, null, Layout.HORIZONTAL);
		Context bottom = new Context("bottom", listOf(contents.get(2), contents.get(3)), true, null, Layout.HORIZONTAL);
		List<XMLElement> out2 = new ArrayList<XMLElement>();
		out2.add(top);
		out2.add(bottom);

		new HopLink(out[0][0], out[1][0],null, null, null, null, Direction.RIGHT);
		new HopLink(out[1][1], out[3][1],null, null, null, null, Direction.DOWN);
		new HopLink(out[2][2], out[3][2],null, null, null, null, Direction.RIGHT);
		new HopLink(out[0][3], out[2][3],null, null, null, null, Direction.DOWN);

		renderDiagram(new Diagram("bob", out2, Layout.VERTICAL, null));

	}

	@Test
	public void test_30_2_BigL() throws IOException {
		List<XMLElement> contents = new ArrayList<XMLElement>();
		Glyph[][] out = GraphConstructionTools.createXContainers("g", 4, 4, contents, null);

		Context top = new Context("top", listOf(contents.get(0), contents.get(1)), true, null, Layout.HORIZONTAL);
		Context bottom = new Context("bottom", listOf(contents.get(2), contents.get(3)), true, null, Layout.HORIZONTAL);
		List<XMLElement> out2 = new ArrayList<XMLElement>();
		out2.add(top);
		out2.add(bottom);

		new Link(out[0][0], out[1][0],null, null, null, null, Direction.RIGHT);
		new Link(out[0][0], out[1][1],null, null, null, null, Direction.RIGHT);
		new Link(out[0][0], out[2][0],null, null, null, null, Direction.DOWN);
		new Link(out[0][0], out[2][1],null, null, null, null, Direction.DOWN);
		new Link(out[2][1], out[3][0],null, null, null, null, Direction.LEFT);
		new Link(out[2][0], out[3][1],null, null, null, null, Direction.LEFT);

		renderDiagram(new Diagram("bob", out2, Layout.VERTICAL, null));

	}
	
	
	
	@Test
	public void test_30_4_DifferentContainerDepths() throws IOException {
		List<XMLElement> contents = new ArrayList<XMLElement>();
		Glyph[][] out = GraphConstructionTools.createXContainers("g", 4, 2, contents, null);

		Glyph[] out2 = GraphConstructionTools.createX("h", 12, contents);
		
		new Link(out[0][0], out2[8],null, null, null, null, Direction.LEFT);
		new Link(out[0][1], out2[9],null, null, null, null, Direction.LEFT);
		new Link(out[0][2], out2[10],null, null, null, null, Direction.LEFT);
		new Link(out[0][3], out2[11],null, null, null, null, Direction.LEFT);
		
		new Link(out[0][0], out2[0],null, null, null, null, Direction.RIGHT);
		new Link(out[0][1], out2[1],null, null, null, null, Direction.RIGHT);
		new Link(out[0][2], out2[2],null, null, null, null, Direction.RIGHT);
		new Link(out[0][3], out2[3],null, null, null, null, Direction.RIGHT);
		
		new Link(out[1][0], out2[0],null, null, null, null, Direction.LEFT);
		new Link(out[1][1], out2[1],null, null, null, null, Direction.LEFT);
		new Link(out[1][2], out2[2],null, null, null, null, Direction.LEFT);
		new Link(out[1][3], out2[3],null, null, null, null, Direction.LEFT);
		

		new Link(out[1][0], out2[6],null, null, null, null, Direction.RIGHT);
		new Link(out[1][1], out2[4],null, null, null, null, Direction.RIGHT);
		new Link(out[1][2], out2[7],null, null, null, null, Direction.RIGHT);
		new Link(out[1][3], out2[5],null, null, null, null, Direction.RIGHT);
		
		renderDiagram(new Diagram("bob", contents, null));

	}
	
	@Test
	public void test_30_5_SimpleOrderingLinkPromotion() throws IOException {
		Glyph a = new Glyph("a", "", "a", null, null);
		Glyph b = new Glyph("b", "", "b", null, null);
		Glyph c = new Glyph("c", "", "c", null, null);
	
		Context top = new Context("top", listOf(a, b), true, null, Layout.RIGHT);
		Context bottom = new Context("bottom", listOf(c), true, null, Layout.HORIZONTAL);
		List<XMLElement> out2 = new ArrayList<XMLElement>();
		out2.add(top);
		out2.add(bottom);
	
		new Link(a, b, null, null, null, null, null);
		new Link(a,c, null, null, null, null, null);
	
		renderDiagram(new Diagram("bob", out2, Layout.DOWN, null));
	}
}
