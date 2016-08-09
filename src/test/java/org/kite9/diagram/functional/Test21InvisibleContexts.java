package org.kite9.diagram.functional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kite9.diagram.adl.CompositionalDiagramElement;
import org.kite9.diagram.adl.Connected;
import org.kite9.diagram.position.Direction;
import org.kite9.diagram.position.Layout;
import org.kite9.diagram.xml.Arrow;
import org.kite9.diagram.xml.ContainerProperty;
import org.kite9.diagram.xml.Context;
import org.kite9.diagram.xml.Diagram;
import org.kite9.diagram.xml.Glyph;
import org.kite9.diagram.xml.Link;
import org.kite9.diagram.xml.LinkEndStyle;
import org.kite9.diagram.xml.TextLine;
import org.kite9.diagram.xml.XMLElement;

public class Test21InvisibleContexts extends AbstractFunctionalTest {

	int id = 0;
	
	
	List<XMLElement> createGlyphs(int count) {
		List<XMLElement> out = new ArrayList<XMLElement>(count);
		for (int i = 0; i < count; i++) {
			id ++;
			Glyph g = new Glyph("id"+id, "bob", "id"+id, null, null);
			List<CompositionalDiagramElement> textLines = new ArrayList<>();
			for (int j = 0; j < i; j++) {
				textLines.add(new TextLine("Some text"));
			}
			ContainerProperty<XMLElement> cp = new ContainerProperty<XMLElement>("text-lines", ContainerProperty.TESTING_DOCUMENT, textLines);
			g.setText(cp);
			out.add(g);
		}
		return out;
	}
	
	
	@Test
	public void test_21_1_3x3NoLinks() throws IOException {
		List<XMLElement> row1 = createGlyphs(3);
		List<XMLElement> row2 = createGlyphs(3);
		List<XMLElement> row3 = createGlyphs(3);
		
		Context c1 = new Context("ctx1", row1, false, null, Layout.RIGHT);
		Context c2 = new Context("ctx2", row2, false, null, Layout.RIGHT);
		Context c3 = new Context("ctx3", row3, false, null, Layout.RIGHT);
		Context cc = new Context("ctxn", createList((XMLElement) c1, c2, c3), true, null, Layout.DOWN);
		
		Diagram d = new Diagram("d", createList((XMLElement) cc), null);
		renderDiagram(d);
		
	}
	
	@Test
	public void test_21_2_Nx3NoLinks() throws IOException {
		List<XMLElement> row1 = createGlyphs(8);
		List<XMLElement> row2 = createGlyphs(7);
		List<XMLElement> row3 = createGlyphs(5);
		
		Context c1 = new Context("ctx1", row1, false, null, Layout.RIGHT);
		Context c2 = new Context("ctx2", row2, false, null, Layout.RIGHT);
		Context c3 = new Context("ctx3", row3, false, null, Layout.RIGHT);
		Context cc = new Context("ctxn", createList((XMLElement) c1, c2, c3), true, null, Layout.DOWN);
		
		Diagram d = new Diagram("d", createList((XMLElement) cc), null);
		renderDiagram(d);
		
	}
	
	@Test
	public void test_21_3_Nx3SomeLinksWithDirectedContext() throws IOException {
		List<XMLElement> row1 = createGlyphs(8);
		List<XMLElement> row2 = createGlyphs(7);
		List<XMLElement> row3 = createGlyphs(15);
		
		Context c1 = new Context("ctx1", row1, true, null, Layout.RIGHT);
		Context c2 = new Context("ctx2", row2, true, null, Layout.RIGHT);
		Context c3 = new Context("ctx3", row3, true, null, Layout.RIGHT);
		Context cc = new Context("ctxn", createList((XMLElement) c1, c2, c3), true, null, Layout.DOWN);
		
//		new Link(c1, c2, null, null, null, null, Direction.DOWN);
//		new Link(c2, c3, null, null, null, null, Direction.DOWN);
		
		
		new Link((Connected) c1.getContents().get(2), (Connected) c2.getContents().get(3), null, null, null, null, null);
//		new Link((Connected) c1.getContents().get(4), (Connected) c2.getContents().get(3), null, null, null);
		new Link((Connected) c1.getContents().get(4), (Connected) c3.getContents().get(4), null, null, null, null, null);
//		
		Diagram d = new Diagram("d", createList((XMLElement) cc), Layout.DOWN, null);
		renderDiagram(d);
		
	}
	
	@Test
	public void test_21_4_Nx3SomeLinksWithLinkedContexts() throws IOException {
		List<XMLElement> row1 = createGlyphs(8);
		List<XMLElement> row2 = createGlyphs(7);
		List<XMLElement> row3 = createGlyphs(15);
		
		Context c1 = new Context("ctx1", row1, true, null, Layout.RIGHT);
		Context c2 = new Context("ctx2", row2, true, null, Layout.RIGHT);
		Context c3 = new Context("ctx3", row3, true, null, Layout.RIGHT);
		Context cc = new Context("ctxn", createList((XMLElement) c1, c2, c3), true, null, null);
		
		new Link(c1, c2, null, null, null, null, Direction.DOWN);
		new Link(c2, c3, null, null, null, null, Direction.DOWN);
		
		
		new Link((Connected) c1.getContents().get(2), (Connected) c2.getContents().get(3), null, null, null, null, null);
//		new Link((Connected) c1.getContents().get(4), (Connected) c2.getContents().get(3), null, null, null);
		new Link((Connected) c1.getContents().get(4), (Connected) c3.getContents().get(4), null, null, null, null, null);
//		
		Diagram d = new Diagram("d", createList((XMLElement) cc), Layout.DOWN, null);
		renderDiagram(d);
		
	}
	
	@Test
	public void test_21_4_hiddenContext() throws IOException {
		Glyph rs = new Glyph("ridley_scott", "Director", "Ridley Scott", null, null);
		Arrow directed = new Arrow("directed");
		
		new Link(directed, rs);

		Glyph bladerunner = new Glyph("film", "Bladerunner", null, null);
		Glyph glad = new Glyph("film", "Gladiator", null, null);
		Glyph thelma = new Glyph("film", "Thelma & Louise", null, null);

		new TurnLink(directed, bladerunner, null, null, LinkEndStyle.ARROW, null);
		new TurnLink(directed, glad, null, null, LinkEndStyle.ARROW, null);
		new TurnLink(directed, thelma, null, null, LinkEndStyle.ARROW, null);

		Context hidden = new Context(listOf(bladerunner, glad, thelma), false, null, Layout.RIGHT);
		
		Diagram d1 = new Diagram("my_diagram", listOf(rs, directed, hidden), null);
		renderDiagram(d1);
	
}
}
