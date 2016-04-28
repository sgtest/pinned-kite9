package org.kite9.diagram.functional;

import java.io.IOException;

import org.junit.Test;
import org.kite9.diagram.adl.Arrow;
import org.kite9.diagram.adl.Context;
import org.kite9.diagram.adl.Diagram;
import org.kite9.diagram.adl.Glyph;
import org.kite9.diagram.adl.Link;
import org.kite9.diagram.position.Direction;
import org.kite9.diagram.primitives.Contained;


public class Test11MultiFaceDirectedArrows extends AbstractFunctionalTest {

	@Test
	public void test_11_1_SeparatedLinks() throws IOException {
		
		Glyph inside = new Glyph("inside", "", "inside", null, null);
		Glyph outside = new Glyph("outside", "", "outside", null, null);
		
		Arrow i1 = new Arrow("i1", "i1");
		Arrow o1 = new Arrow("o1", "o1");
		
		new Link(inside, i1, null, null, null, null, Direction.RIGHT);
		new Link(outside, o1, null, null, null, null, Direction.LEFT);
		new Link(inside, outside);
		
		
		Contained c1 = new Context("if", createList((Contained) inside, i1), true, null, null);
		
		Diagram d = new Diagram("The Diagram", createList(c1, outside, o1), null);
		renderDiagram(d);
	}

	
	@Test
	public void test_11_2_SeparatedLinksMultipleContainers() throws IOException {
		
		Glyph inside = new Glyph("inside", "", "inside", null, null);
		Glyph outside = new Glyph("outside", "", "outside", null, null);
		
		Arrow i1 = new Arrow("i1","i1");
		Arrow o1 = new Arrow("o1", "o1");
		
		new Link(inside, i1, null, null, null, null, Direction.RIGHT);
		new Link(outside, o1, null, null, null, null, Direction.LEFT);
		new Link(inside, outside);
		
		
		Contained c1 = new Context("if 1", createList((Contained) inside, i1), true, null,null);
		Contained c2 = new Context("if 2", createList(c1), true, null,null);
		Contained c3 = new Context("if 3", createList(c2), true, null,null);
		Contained c4 = new Context("if 4", createList(c3), true, null,null);
		Contained c5 = new Context("if 5", createList(c4), true, null,null);
		
		
		
		Diagram d = new Diagram("The Diagram", createList(c5, outside, o1), null);
		renderDiagram(d);
	}
	
	@Test
	public void test_11_3_DirectedLinksMultipleContainers() throws IOException {
		
		Glyph inside = new Glyph("inside", "", "inside", null, null);
		Glyph outside = new Glyph("outside", "", "outside", null, null);
		
		Arrow i1 = new Arrow("i1", "i1");
		Arrow o1 = new Arrow("o1", "o1");
		
		new Link(inside, i1, null, null , null, null, Direction.RIGHT);
		new Link(outside, o1, null, null, null, null, Direction.LEFT);
		new Link(inside, outside, null, null, null, null, Direction.UP);
		
		
		Contained c1 = new Context("if 1", createList((Contained) inside, i1), true, null, null);
		Contained c2 = new Context("if 2", createList(c1), true, null, null);
		Contained c3 = new Context("if 3", createList(c2), true, null, null);
		Contained c4 = new Context("if 4", createList(c3), true, null, null);
	//	Contained c5 = new Context("if 5", createList(c4), true);
		
		
		
		Diagram d = new Diagram("The Diagram", createList(c4, outside, o1), null);
		renderDiagram(d);
	}
	
	
}
