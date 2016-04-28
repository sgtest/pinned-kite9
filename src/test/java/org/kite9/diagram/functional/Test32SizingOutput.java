package org.kite9.diagram.functional;

import java.io.IOException;

import org.junit.Test;
import org.kite9.diagram.adl.Arrow;
import org.kite9.diagram.adl.Context;
import org.kite9.diagram.adl.Diagram;
import org.kite9.diagram.adl.Glyph;
import org.kite9.diagram.adl.Link;
import org.kite9.diagram.adl.LinkEndStyle;
import org.kite9.diagram.adl.Symbol;
import org.kite9.diagram.adl.Symbol.SymbolShape;
import org.kite9.diagram.adl.TextLine;
import org.kite9.diagram.visualization.display.java2d.GriddedCompleteDisplayer;
import org.kite9.diagram.visualization.display.java2d.adl_basic.ADLBasicCompleteDisplayer;
import org.kite9.diagram.visualization.display.java2d.style.Stylesheet;
import org.kite9.diagram.visualization.display.java2d.style.sheets.BasicStylesheet;
import org.kite9.diagram.visualization.format.png.BufferedImageRenderer;
import org.kite9.diagram.visualization.pipeline.full.BufferedImageProcessingPipeline;

public class Test32SizingOutput extends AbstractFunctionalTest {
	@Test
	public void test_32_1_TestSizesAreCreated() throws IOException {
		Diagram d = createADiagram();
		renderDiagramSizes(d, new BasicStylesheet());

	}
	
	@Test
	public void test_32_2_TestMapIsCreated() throws IOException {
		Diagram d = createADiagram();
		renderMap(d, new BasicStylesheet());
	}
	
	@Test
	public void test_32_3_TestDiagramSizeCanBeSet() throws IOException {
		Diagram d = createADiagram();
		TestingEngine te = getTestingEngineSettingSize(900, 200);
		renderDiagram(d, te, false, new BasicStylesheet());
 	}
	
	@Test
	public void test_32_4_TestDiagramHeightScaling() throws IOException {
		Diagram d = createADiagram();
		TestingEngine te = getTestingEngineSettingSize(200, 400);
		renderDiagram(d, te, false, new BasicStylesheet());
 	}
	
	@Test
	public void test_32_5_TestDiagramHeightAndWidthScaling() throws IOException {
		Diagram d = new Diagram();
		d.getContents().add(new Glyph("", "New Part", null, null, null));
		TestingEngine te = getTestingEngineSettingSize(200, 200);
		renderDiagram(d, te, false, new BasicStylesheet());
 	}

	protected TestingEngine getTestingEngineSettingSize(final int width, final int height) {
		TestingEngine te = new TestingEngine(getZipName()) {

			@Override
			public BufferedImageProcessingPipeline getPipeline(Stylesheet ss,
					Class<?> test, String subtest, boolean watermark) {
				return new BufferedImageProcessingPipeline(new GriddedCompleteDisplayer(new ADLBasicCompleteDisplayer(ss, watermark, false), ss), 
						new BufferedImageRenderer(width, height));
			}
		};
		return te;
	}
	
	

	protected Diagram createADiagram() {
		Glyph one = new Glyph("Stereo", "One", createList(
				new TextLine("Here is line 1", createList(new Symbol(
						"Some text", 'a', SymbolShape.CIRCLE), new Symbol(
						"Some text", 'A', SymbolShape.DIAMOND), new Symbol(
						"Some text", 'A', SymbolShape.HEXAGON))), new TextLine(
						"Here is line 2"), new TextLine("Here is line 3")),
						
				createList(new Symbol("Some text", 'q', SymbolShape.DIAMOND)));

		Arrow two = new Arrow("a1", "Some Arrow");
		
		//Link l =
			new Link(one, two, LinkEndStyle.ARROW, new TextLine("This is one end"),null, null);

		Context c = new Context("c1", listOf(one), true, new TextLine("This is the context"), null);
		
		Diagram d = new Diagram("The Diagram", listOf(c, two), null);
		return d;
	}

}