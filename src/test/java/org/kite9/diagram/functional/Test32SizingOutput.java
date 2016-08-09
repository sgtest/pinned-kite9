package org.kite9.diagram.functional;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.kite9.diagram.visualization.display.complete.ADLBasicCompleteDisplayer;
import org.kite9.diagram.visualization.display.complete.GriddedCompleteDisplayer;
import org.kite9.diagram.visualization.format.png.BufferedImageRenderer;
import org.kite9.diagram.visualization.pipeline.full.BufferedImageProcessingPipeline;
import org.kite9.diagram.xml.Arrow;
import org.kite9.diagram.xml.Context;
import org.kite9.diagram.xml.Diagram;
import org.kite9.diagram.xml.Glyph;
import org.kite9.diagram.xml.Link;
import org.kite9.diagram.xml.LinkEndStyle;
import org.kite9.diagram.xml.Symbol;
import org.kite9.diagram.xml.TextLine;
import org.kite9.diagram.xml.Symbol.SymbolShape;
import org.kite9.framework.common.HelpMethods;

public class Test32SizingOutput extends AbstractFunctionalTest {

	@Ignore("Broken in sprint 7")
	@Test
	public void test_32_1_TestSizesAreCreated() throws IOException {
		Diagram d = createADiagram();
		renderDiagramSizes(d);

	}
	
	@Ignore("Broken in sprint 7")
	@Test
	public void test_32_2_TestMapIsCreated() throws IOException {
		Diagram d = createADiagram();
		renderMap(d);
	}
	
	@Test
	public void test_32_3_TestDiagramSizeCanBeSet() throws IOException {
		Diagram d = createADiagram();
		TestingEngine te = getTestingEngineSettingSize(900, 200);
		renderDiagram(d, te, false);
 	}
	
	@Test
	public void test_32_4_TestDiagramHeightScaling() throws IOException {
		Diagram d = createADiagram();
		TestingEngine te = getTestingEngineSettingSize(200, 400);
		renderDiagram(d, te, false);
 	}
	
	@Test
	public void test_32_5_TestDiagramHeightAndWidthScaling() throws IOException {
		Diagram d = new Diagram("blo", HelpMethods.listOf(new Glyph("", "New Part", null, null)));
		TestingEngine te = getTestingEngineSettingSize(200, 200);
		renderDiagram(d, te, false);
 	}

	protected TestingEngine getTestingEngineSettingSize(final int width, final int height) {
		TestingEngine te = new TestingEngine(getZipName()) {

			@Override
			public BufferedImageProcessingPipeline getPipeline(
					Class<?> test, String subtest, boolean watermark) {
				return new BufferedImageProcessingPipeline(new GriddedCompleteDisplayer(new ADLBasicCompleteDisplayer(watermark, false)), 
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