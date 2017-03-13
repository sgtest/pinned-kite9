package org.kite9.diagram.functional.layout;

import org.junit.Before;
import org.junit.Test;
import org.kite9.diagram.adl.Arrow;
import org.kite9.diagram.adl.Context;
import org.kite9.diagram.adl.Glyph;
import org.kite9.diagram.adl.Link;
import org.kite9.diagram.adl.TextLine;
import org.kite9.diagram.functional.AbstractLayoutFunctionalTest;
import org.kite9.diagram.position.Direction;
import org.kite9.diagram.position.Layout;
import org.kite9.diagram.xml.DiagramXMLElement;
import org.kite9.diagram.xml.LinkEndStyle;

public class Test23LinkBucketing extends AbstractLayoutFunctionalTest {

	// the players

	Arrow one;
	//Glyph one;
	Glyph two;
	Glyph three;
	Glyph four;
	Glyph five;
	Glyph six;
	Glyph seven;

	Context con1;
	DiagramXMLElement d;

	@Before
	public void setUp() {
		one = new Arrow("a1", "a1");
		// one = new Glyph("g1", "", "one", null, null);
		two = new Glyph("g2", "", "two", null, null);
		three = new Glyph("g3", "", "three", null, null);
		four = new Glyph("g4", "", "four", null, null);
		five = new Glyph("g5", "", "five", null, null);
		six = new Glyph("g6", "", "six", null, null);
		seven = new Glyph("g7", "", "seven", null, null);
		
		con1 = new Context("b1", listOf(one, two, three, four, five, six, seven),
				true, new TextLine("inside"), Layout.RIGHT);
		d = new DiagramXMLElement("The Diagram", listOf(con1), null);
	}

	@Test
	public void test_23_1_ConnectingToOne() throws Exception {
		new Link(one, two);
		new Link(one, three);
		new Link(one, four);
		new Link(one, five);
		new Link(one, six);
		new Link(one, seven);
		
		renderDiagram(d);
	}
	
	@Test
	public void test_23_2_SomeArrowHeads() throws Exception {
		new Link(one, two, LinkEndStyle.ARROW, null, null, null, null);
		new Link(one, three);
		new Link(one, four);
		new Link(one, five, LinkEndStyle.ARROW, null, null, null, null);
		new Link(one, six);
		new Link(one, seven);
		
		renderDiagram(d);
	}
	
	@Test
	public void test_23_3_AwkwardCuss() throws Exception {
		new Link(one, two, LinkEndStyle.ARROW, null, null, null, null);
		new Link(one, three, null, null, null, null, Direction.RIGHT);
		new Link(one, four);
		new Link(one, five, LinkEndStyle.ARROW, null, null, null, null);
		new Link(one, six);
		new Link(one, seven);
		
		renderDiagram(d);
	}

	@Override
	protected boolean checkEverythingStraight() {
		return false;
	}
	
	
}
