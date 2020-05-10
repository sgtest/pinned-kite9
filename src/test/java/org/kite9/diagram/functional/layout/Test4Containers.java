package org.kite9.diagram.functional.layout;

import org.junit.Test;
import org.kite9.diagram.AbstractLayoutFunctionalTest;
import org.kite9.diagram.adl.Context;
import org.kite9.diagram.adl.DiagramKite9XMLElement;
import org.kite9.diagram.adl.Glyph;
import org.kite9.diagram.adl.Link;
import org.kite9.diagram.adl.TextLabel;
import org.kite9.diagram.adl.TextLine;
import org.kite9.diagram.model.position.Layout;
import org.kite9.diagram.model.style.LabelPlacement;

public class Test4Containers extends AbstractLayoutFunctionalTest {

	@Test
	public void test_4_1_ContainerNestingFinal() throws Exception {
		Glyph one = new Glyph("Stereo", "Rob's Glyph", null, null);
		Context con1 = new Context("b1", createList(one), true, null, null);
		Context con2 = new Context("b2", createList(con1), true, null, null);
		Context con3 = new Context("b3", createList(con2), true, null, null);

		DiagramKite9XMLElement d = new DiagramKite9XMLElement("The Diagram", createList(con3), null);
		renderDiagram(d);
	}

	@Test
	public void test_4_2_HierarchicalContainers() throws Exception {
		Glyph one = new Glyph("Stereo", "one", null, null);
		Glyph two = new Glyph("Stereo", "two", null, null);
		Context con1 = new Context("b1", createList(one), true, null, null);
		Context con2 = new Context("b2", createList(two), true, null, null);
		Context con3 = new Context("b3", createList(con1, con2), true, null, null);

		DiagramKite9XMLElement d = new DiagramKite9XMLElement("The Diagram", createList(con3), null);
		renderDiagram(d);
	}
	
	
	@Test
	public void test_4_3_ContainerContentOrdering() throws Exception {
		Glyph one = new Glyph("top", "Stereo", "top", null, null);
		Glyph two = new Glyph("middle", "Stereo", "middle", null, null);
		Glyph three = new Glyph("bottom", "Stereo", "bottom", null, null);
		
		Context con1 = new Context("b1", createList(one, two, three), true, null, Layout.DOWN);
		
		Glyph four = new Glyph("left", "Stereo", "left", null, null);
		Glyph five = new Glyph("middle2", "Stereo", "middle2", null, null);
		Glyph six = new Glyph("right", "Stereo", "right", null, null);
		
		
		Context con2 = new Context("b2", createList(four, five, six), true, null, Layout.RIGHT);
	
		Context con3 = new Context("b3", createList(con1, con2), true, null, Layout.UP);

		DiagramKite9XMLElement d = new DiagramKite9XMLElement("The Diagram", createList(con3), null);
		renderDiagram(d);
	}

	@Test
	public void test_4_4_LabelledContainers() throws Exception {
		Glyph one = new Glyph("Stereo", "one", null, null);
		Glyph two = new Glyph("Stereo", "two", null, null);
		Context con1 = new Context("b1", 
			createList(one, 
					new TextLabel("Top Left", LabelPlacement.TOP_LEFT),
					new TextLabel("Top Right", LabelPlacement.TOP_RIGHT),
					new TextLabel("Bottom Left", LabelPlacement.BOTTOM_LEFT),
					new TextLabel("Bottom Right", LabelPlacement.BOTTOM_RIGHT)), true, null, null);
		
		TextLabel top = new TextLabel("Top", LabelPlacement.TOP);
		TextLabel right = new TextLabel("Right", LabelPlacement.RIGHT);
		TextLabel tall = new TextLabel("Tall", LabelPlacement.RIGHT);
		TextLabel down = new TextLabel("Bottom", LabelPlacement.BOTTOM);
		
		top.setAttribute("style", "kite9-horizontal-sizing: maximize; kite9-label-placement: top;");
		right.setAttribute("style", "kite9-horizontal-sizing: maximize; kite9-label-placement: right;");
		
		tall.setAttribute("style", "kite9-vertical-sizing: maximize; kite9-label-placement: right;");
		down.setAttribute("style", "kite9-horizontal-sizing: minimize; kite9-label-placement: bottom;");
		
		Context con2 = new Context("b2", 
				createList(two, 
						top,
						right,
						tall,
						down), true, null, null);

		
		Context con3 = new Context("b3", 
				createList(new TextLabel("Top \n1", LabelPlacement.TOP),
						new TextLabel("Top 2", LabelPlacement.TOP),
						new TextLabel("Left 1", LabelPlacement.LEFT),
						new TextLabel("Left \n2", LabelPlacement.LEFT)), true, null, null);
		
		new Link(one, two);
		DiagramKite9XMLElement d = new DiagramKite9XMLElement("The Diagram", createList(
				con1, 
				con2, 
				con3
				), null);
		
		renderDiagram(d);
	}
	
	// top-to-bottom and left-to-right ordering

	// invisible containers
}
