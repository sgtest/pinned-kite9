package org.kite9.diagram.functional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.kite9.diagram.adl.Context;
import org.kite9.diagram.adl.Glyph;
import org.kite9.diagram.adl.Link;
import org.kite9.diagram.adl.TextLine;
import org.kite9.diagram.position.Direction;
import org.kite9.diagram.position.Layout;
import org.kite9.diagram.xml.DiagramXMLElement;
import org.kite9.diagram.xml.XMLElement;


public class Test51Grid extends AbstractFunctionalTest {

	
	@Test
	public void test_51_1_SimpleGrid() throws IOException {
		Context tl = new Context("tl", null, true,  null, null);
		Context tr = new Context("tr", null, true,  null, null);
		Context bl = new Context("bl", null, true,  null, null);
		Context br = new Context("br", null, true,  null, null);
		
		tr.setStyle("occupies-x: 1; occupies-y: 0;");
		bl.setStyle("occupies-x: 0; occupies-y: 1 1;");
		br.setStyle("occupies-x: 1; occupies-y: 1;");
		
		Context ctx = new Context("outer", Arrays.asList(tl, tr, bl, br), true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-columns: 2;"); 
		
		
		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx), null));
	}
	
	@Test
	public void test_51_2_SupergridMockup() throws IOException {
		Context ctx = createSupergrid(true, false);
		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx), null));
	}

	private Context createSupergrid(boolean addLinks, boolean addContentLink) {
		List<XMLElement> contents = new ArrayList<>();
		Context[][] elems = new Context[4][];
		for (int i = 0; i < elems.length; i++) {
			elems[i] = new Context[4];
			for (int j = 0; j < elems[i].length; j++) {
				elems[i][j] = new Context("c" + i + "-" + j, null, true,  null , null);
				elems[i][j].setStyle("occupies: "+i+" "+i+" "+j+" "+j+";");
				if (addLinks) {
					if (j > 0) {
						new Link((XMLElement) elems[i][j], (XMLElement) elems[i][j - 1], "", null, "", null, Direction.RIGHT);
					}
					if (i > 0) {
						new Link((XMLElement) elems[i][j], (XMLElement) elems[i - 1][j], "", null, "", null, Direction.UP);
					}
				}
				
				contents.add(elems[i][j]);
			}
		}
		
		
		Glyph g1 = new Glyph("one", "","Some gylph", null, null);
		Glyph g2 = new Glyph("two", "","sdlfkjsdlkfsdlkfk lksdjf ", null, null);
		Glyph g3 = new Glyph("three", "","sdlfkjsdlkfsdlkfk lksdjf ", null, null);
		Glyph g4 = new Glyph("four", "","sdlfkjsdlkfsdlkfk lksdjf ", null, null);
		elems[2][1].appendChild(g1);
		elems[1][3].appendChild(g2);
		elems[3][0].appendChild(g3);
		elems[0][0].appendChild(g4);
		
		if (addContentLink) {
			new TurnLink(g1, g2);
			new TurnLink(g1, g2);
			new TurnLink(g1, g3);
			new TurnLink(g1, g4);
			new TurnLink(g2, g4);
		}
		
		
		Context ctx = new Context("outer", contents, true, null, null);
		return ctx;
	}
	
	@Test
	public void test_51_3_GridWithMissingBits() throws IOException {
		Context tl = new Context("tl", null, true,  new TextLine("Top \n Left"), null);
		Context tr = new Context("tr", null, true,  new TextLine("Top Right"), null);
		Context br = new Context("br", null, true,  new TextLine("Bottom Right"), null);
		tl.setStyle("occupies-x: 0; occupies-y: 0;");
		tr.setStyle("occupies-x: 1; occupies-y: 0;");
		br.setStyle("occupies-x: 1; occupies-y: 1;");
		
		Context ctx = new Context("inner", Arrays.asList(tl, tr, br), true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 2 2;"); 
		
		
		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx), null));
	}
	

	@Test
	public void test_51_4_ProperSupergrid() throws IOException {
		Context ctx = createSupergrid(false, false);
		ctx.setStyle("layout: grid; grid-size: 4 4;"); 
		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx), null));
	}
	
	@Test
	public void test_51_5_GridWithSpanningSquares() throws IOException {
		Context tl = new Context("tl", null, true,  new TextLine("Top \n Left"), null);
		Context tr = new Context("tr", null, true,  new TextLine("Top Right"), null);
		Context bl = new Context("bl", null, true,  new TextLine("Bottom Left"), null);
		Context br = new Context("br", null, true,  new TextLine("Bottom Right"), null);
		
		tl.setStyle("occupies: 0 1 0 1;");
		tr.setStyle("occupies: 2 0;");
		
		bl.setStyle("occupies: 0 1 2 2");
		br.setStyle("occupies: 2 2 1 2");
		
		Context ctx = new Context("outer", Arrays.asList(tl, tr, bl, br), true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;"); 

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx), null));
	}
	
	@Test
	public void test_51_6_GridWithUndirectedConnections() throws IOException {
		Context ctx = createSupergrid(false, true);
		ctx.setStyle("layout: grid; grid-size: 4 4;"); 
		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx), null));
	}
	
	@Test
	public void test_51_7_GridWithDirectedConnections() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four", null, null);
		
		Context tl = new Context("tl", listOf(g1), true,  new TextLine("Top \n Left"), null);
		Context tr = new Context("tr", listOf(g2), true,  new TextLine("Top Right"), null);
		Context bl = new Context("bl", listOf(g3), true,  new TextLine("Bottom Left"), null);
		Context br = new Context("br", listOf(g4), true,  new TextLine("Bottom Right"), null);
		
		tl.setStyle("occupies: 0 1 0 1;");
		tr.setStyle("occupies: 2 0;");
		
		bl.setStyle("occupies: 0 1 2 2");
		br.setStyle("occupies: 2 2 1 2");
		
		Context ctx = new Context("outer", Arrays.asList(tl, tr, bl, br), true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;"); 
		
		new Link(g1, g2, null, null, null, null, Direction.RIGHT);
		new Link(g1, g3, null, null, null, null, Direction.DOWN);
		new Link(g1, g4, null, null, null, null, Direction.RIGHT);
		new Link(g2, g4, null, null, null, null, Direction.DOWN);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx), null));
	}
	
	private List<XMLElement> createContexts(Glyph g1, Glyph g2, Glyph g3, Glyph g4) {
		Context tl = new Context("tl", listOf(g1), true,  null, null);
		Context tr = new Context("tr", listOf(g2), true,  new TextLine("Top Right"), null);
		Context bl = new Context("bl", listOf(g3), true,  new TextLine("Bottom Left"), null);
		Context br = new Context("br", listOf(g4), true,  new TextLine("Bottom Right"), null);
		
		tl.setStyle("occupies: 0 1 0 1;");
		tr.setStyle("occupies: 2 0;");
		
		bl.setStyle("occupies: 0 1 2 2");
		br.setStyle("occupies: 2 2 1 2");
		
		List<XMLElement> contexts = Arrays.asList(tl, tr, bl, br);
		return contexts;
	}
	
	@Test
	public void test_51_9_IllegalDirectedConnections() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		
		Context ctx = setupContext(g1, g2, g3, g4); 
		
		new ContradictingLink(g1, g3, null, null, null, null, Direction.RIGHT);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx), null));
	}
	
	@Test
	public void test_51_10_IllegalDirectedConnections2() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		
		Context ctx = setupContext(g1, g2, g3, g4); 
		
		new ContradictingLink(g2, g3, null, null, null, null, Direction.RIGHT);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx), null));
	}

	private Context setupContext(Glyph g1, Glyph g2, Glyph g3, Glyph g4) {
		Context ctx = new Context("outer", createContexts(g1, g2, g3, g4), true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;");
		return ctx;
	}
	
	@Test
	public void test_51_11_ContainerConnectionInsideDirected() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		Glyph g5 = new Glyph("five", "","five ", null, null);
		List<XMLElement> contexts = createContexts(g1, g2, g3, g4);
		Context ctx = new Context("outer", contexts, true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;");
		
		new ContradictingLink(contexts.get(0), g3, null, null, null, null, Direction.RIGHT);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx, g5), null));
	}
	
	@Test
	public void test_51_12_ContainerConnectionInsideUndirected() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		Glyph g5 = new Glyph("five", "","five ", null, null);
		List<XMLElement> contexts = createContexts(g1, g2, g3, g4);
		Context ctx = new Context("outer", contexts, true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;");
		
		new ContradictingLink(contexts.get(0), g3, null, null, null, null, null);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx, g5), null));
	}
	
	@Test
	public void test_51_13_ContainerConnectionOutsideUndirected() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		Glyph g5 = new Glyph("five", "","five ", null, null);
		List<XMLElement> contexts = createContexts(g1, g2, g3, g4);
		Context ctx = new Context("outer", contexts, true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;");
		
		new ContradictingLink(contexts.get(3), g5, null, null, null, null, null);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx, g5), null));
	}
	
	@Test
	public void test_51_14_ContainerConnectionOutsideDirectedFacing() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		Glyph g5 = new Glyph("five", "","five ", null, null);
		List<XMLElement> contexts = createContexts(g1, g2, g3, g4);
		Context ctx = new Context("outer", contexts, true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;");
		
		new ContradictingLink(contexts.get(3), g5, null, null, null, null, Direction.RIGHT);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx, g5), null));
	}
	
	@Test
	public void test_51_15_ContainerConnectionOutsideDirectedThroughContainer() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		Glyph g5 = new Glyph("five", "","five ", null, null);
		List<XMLElement> contexts = createContexts(g1, g2, g3, g4);
		Context ctx = new Context("outer", contexts, true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;");
		
		new ContradictingLink(contexts.get(0), g5, null, null, null, null, Direction.RIGHT);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx, g5), null));
	}
	
	@Test
	public void test_51_16_ContainerConnectionParentOutsideUndirected() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		Glyph g5 = new Glyph("five", "","five ", null, null);
		List<XMLElement> contexts = createContexts(g1, g2, g3, g4);
		Context ctx = new Context("outer", contexts, true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;");
		
		new ContradictingLink(ctx, g5, null, null, null, null, null);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx, g5), null));
	}
	
	@Test
	public void test_51_17_ContainerConnectionParentInsideUndirected() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		Glyph g5 = new Glyph("five", "","five ", null, null);
		List<XMLElement> contexts = createContexts(g1, g2, g3, g4);
		Context ctx = new Context("outer", contexts, true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;");
		
		new ContradictingLink(ctx, g2, null, null, null, null, null);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx, g5), null));
	}
	
	@Test
	public void test_51_18_ContainerConnectionParentInsideDirected() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		Glyph g5 = new Glyph("five", "","five ", null, null);
		List<XMLElement> contexts = createContexts(g1, g2, g3, g4);
		Context ctx = new Context("outer", contexts, true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;");
		
		new ContradictingLink(ctx, g2, null, null, null, null, Direction.RIGHT);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx, g5), null));
	}
	
	
	@Test
	public void test_51_19_ContainerConnectionParentOutsideDirected() throws IOException {
		Glyph g1 = new Glyph("one", "","one", null, null);
		Glyph g2 = new Glyph("two", "","two ", null, null);
		Glyph g3 = new Glyph("three", "","three ", null, null);
		Glyph g4 = new Glyph("four", "","four ", null, null);
		Glyph g5 = new Glyph("five", "","five ", null, null);
		List<XMLElement> contexts = createContexts(g1, g2, g3, g4);
		Context ctx = new Context("outer", contexts, true, null, Layout.GRID);
		ctx.setStyle("layout: grid; grid-size: 3 3;");
		
		new ContradictingLink(ctx, g5, null, null, null, null, Direction.DOWN);

		renderDiagram(new DiagramXMLElement("diagram", Arrays.asList(ctx, g5), null));
	}

	
	
}
