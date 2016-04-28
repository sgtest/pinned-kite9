package org.kite9.diagram.functional;

import java.io.IOException;

import org.junit.Test;
import org.kite9.diagram.adl.Arrow;
import org.kite9.diagram.adl.Context;
import org.kite9.diagram.adl.Diagram;
import org.kite9.diagram.adl.Glyph;
import org.kite9.diagram.adl.Key;
import org.kite9.diagram.adl.Link;
import org.kite9.diagram.adl.LinkEndStyle;
import org.kite9.diagram.adl.Symbol;
import org.kite9.diagram.adl.Symbol.SymbolShape;
import org.kite9.diagram.adl.TextLine;
import org.kite9.diagram.primitives.Contained;
import org.kite9.diagram.visualization.display.java2d.style.sheets.Designer2012Stylesheet;
import org.kite9.diagram.visualization.display.java2d.style.sheets.DesignerStylesheet;

public class Test49SVG extends AbstractFunctionalTest {

	@Test
	public void test_49_1_GlyphFinal() throws IOException {
		Contained one = new Glyph("RG", "Stereo", "Rob's Glyph", null, null);
		Diagram d = new Diagram("The Diagram", createList(one), null);

		renderDiagramSVG(d, new Designer2012Stylesheet());
	}

	@Test
	public void test_49_2_GlyphWithTextSymbol() throws IOException {
		Contained one = new Glyph("one", "Stereo", "One", createList(new TextLine("Here is line 1", createList(new Symbol(
				"Some text", 'a', SymbolShape.CIRCLE), new Symbol("Some text", 'A', SymbolShape.DIAMOND), new Symbol(
				"Some text", 'A', SymbolShape.HEXAGON))), new TextLine("Here is line 2"),
				new TextLine("Here is line 3")), createList(new Symbol("Some text", 'q', SymbolShape.DIAMOND)));
		Diagram d = new Diagram("The Diagram", createList(one), null);
		renderDiagramSVG(d);
	}

	@Test
	public void test_49_3_GlyphWithSymbolOnly() throws IOException {
		Contained one = new Glyph("one", "", "One", null, createList(new Symbol("Some text", 'a', SymbolShape.CIRCLE),
				new Symbol("Some text", 'a', SymbolShape.DIAMOND), new Symbol("Some text", 'a', SymbolShape.HEXAGON)));
		Diagram d = new Diagram("The Diagram", createList(one), null);
		renderDiagramSVG(d);
	}

	@Test
	public void test_49_4_KeyWith1Symbol() throws IOException {

		Glyph a = new Glyph("a", "", "a", null, null);

		Key k = new Key("some bold text", null, createList(new Symbol("Some unholy information", 'S',
				SymbolShape.CIRCLE)));

		Diagram d = new Diagram("The Diagram", createList((Contained) a), k);
		renderDiagramSVG(d);
	}

	@Test
	public void test_49_5_SingleContainerLink() throws IOException {

		Glyph g1 = new Glyph("g1", "", "g1", null, null);
		Arrow a = new Arrow("a1", "a1");

		Context con1 = new Context("con1", createList((Contained) g1), true, new TextLine("c1"), null);
		Context con2 = new Context("con2", null, true, new TextLine("c2"), null);
		Context con3 = new Context("con3", createList((Contained) a), true, new TextLine("c3"), null);
		Context con4 = new Context("con4", null, true, new TextLine("c4"), null);
		Context con5 = new Context("con5", null, true, new TextLine("c5"), null);
		new Link(con1, con2, null, new TextLine("arranges"), LinkEndStyle.ARROW, new TextLine("meets"));
		new Link(g1, a, null, new TextLine("g1end"), null, new TextLine("aend"), null);

		Diagram d = new Diagram("D", createList((Contained) con1, con5, con4, con3, con2), null);
		renderDiagramSVG(d, new Designer2012Stylesheet());
	}

	@Test
	public void test_49_6_ArrowOutsideContainer() throws IOException {
		Glyph one = new Glyph("one", "", "one", null, null);
		Glyph two = new Glyph("two", "", "two", null, null);

		Contained con1 = new Context("b1", createList((Contained) one), true, null, null);

		Arrow a = new Arrow("links", "links");

		new Link(a, one);
		new Link(a, two);

		Diagram d = new Diagram("The Diagram", createList(con1, a, two), null);
		renderDiagramSVG(d, new Designer2012Stylesheet());
	}

	@Test
	public void test_49_7_ArrowToMultipleElements() throws IOException {
		Glyph one = new Glyph("one", "", "one", null, null);
		Glyph two = new Glyph("two", "", "two", null, null);
		Glyph three = new Glyph("three", "", "three", null, null);

		Contained con1 = new Context("b1", createList((Contained) one, two, three), true, null, null);

		Arrow a = new Arrow("links", "links");

		new TurnLink(a, one);
		new Link(a, two);
		new TurnLink(a, three);

		Diagram d = new Diagram("The Diagram", createList(con1, a), null);
		renderDiagramSVG(d, new DesignerStylesheet());
	}
}
