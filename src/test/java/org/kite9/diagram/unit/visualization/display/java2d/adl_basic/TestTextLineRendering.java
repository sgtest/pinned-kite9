package org.kite9.diagram.unit.visualization.display.java2d.adl_basic;

import org.junit.Ignore;

@Ignore
public class TestTextLineRendering extends AbstractRenderingTest {

/*	@Test
	public void testContextLabel1() throws IOException {
		Dimension2D size = new Dimension2D(1000, 400);
		BufferedImage bi = newImage(size);
		Graphics2D g2 = bi.createGraphics();	
		setHints(g2);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				testContextLabel(8+i*3, 8+j*3, (j*3)+18, VPos.DOWN, HPos.LEFT, i*200, j*80, g2, "Kellogs", true);
			}	
		}
		TestingEngine te = new TestingEngine(getZipName());
		String imageName = "testContextLabel1.png";
		TestingEngine.renderToFile(getClass(), "testContextLabel", imageName, bi);
		te.checkOutputs(this.getClass(), "testContextLabel", imageName);
	}

	protected BufferedImage newImage(Dimension2D size) {
		return new BufferedImage((int) size.getWidth(), (int) size.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	@Test
	public void testContextLabel2() throws IOException {
		Dimension2D size = new Dimension2D(1000, 400);
		BufferedImage bi = newImage(size);
		Graphics2D g2 = bi.createGraphics();	
		setHints(g2);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				testContextLabel(8+i*3, 8+j*3, (j*3)+18, VPos.DOWN, HPos.LEFT, i*200, j*80, g2, "Kellog\nBriand jem", true);
			}	
		}
		TestingEngine te = new TestingEngine(getZipName());
		String imageName = "testContextLabel2.png";
		TestingEngine.renderToFile(getClass(), "testContextLabel", imageName, bi);
		te.checkOutputs(this.getClass(), "testContextLabel", imageName);
	}
	
	@Test
	public void testContextLabel3() throws IOException {
		Dimension2D size = new Dimension2D(1000, 400);
		BufferedImage bi = newImage(size);
		Graphics2D g2 = bi.createGraphics();	
		setHints(g2);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				testContextLabel(8+i*3, 8+j*3, (j*3)+18, VPos.DOWN, HPos.LEFT, i*200, j*80, g2, "Kellogs", false);
			}	
		}
		TestingEngine te = new TestingEngine(getZipName());
		String imageName = "testContextLabel3.png";
		TestingEngine.renderToFile(getClass(), "testContextLabel", imageName, bi);
		te.checkOutputs(this.getClass(), "testContextLabel", imageName);
	}
	
	@Test
	public void testContextLabel4() throws IOException {
		Dimension2D size = new Dimension2D(1000, 400);
		BufferedImage bi = newImage(size);
		Graphics2D g2 = bi.createGraphics();	
		setHints(g2);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				testContextLabel(8+i*3, 8+j*3, (j*3)+18, VPos.DOWN, HPos.LEFT, i*200, j*80, g2, "Kellog\nBriand jem", false);
			}	
		}
		TestingEngine te = new TestingEngine(getZipName());
		String imageName = "testContextLabel4.png";
		TestingEngine.renderToFile(getClass(), "testContextLabel", imageName, bi);
		te.checkOutputs(this.getClass(), "testContextLabel", imageName);
	}
	
	@Test
	public void testConnectionLabelLeftTop() throws IOException {
		Dimension2D size = new Dimension2D(1000, 400);
		BufferedImage bi = newImage(size);
		Graphics2D g2 = bi.createGraphics();	
		setHints(g2);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				testConnectionLabel(8+i*3, 8+j*3, (j*3)+18, VPos.UP, HPos.LEFT, i*200, j*80, g2, true);
			}	
		}
		TestingEngine te = new TestingEngine(getZipName());
		String imageName = "testLeftTop.png";
		TestingEngine.renderToFile(getClass(), "testConnectionLabel", imageName, bi);
		te.checkOutputs(this.getClass(), "testConnectionLabel", imageName);
	}
	
	@Test
	public void testConnectionLabelLeftTopNoSyms() throws IOException {
		Dimension2D size = new Dimension2D(1000, 400);
		BufferedImage bi = newImage(size);
		Graphics2D g2 = bi.createGraphics();	
		setHints(g2);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				testConnectionLabel(8+i*3, 8+j*3, (j*3)+18, VPos.UP, HPos.LEFT, i*200, j*80, g2, false);
			}	
		}
		TestingEngine te = new TestingEngine(getZipName());
		String imageName = "testLeftTopNoSyms.png";
		TestingEngine.renderToFile(getClass(), "testConnectionLabel", imageName, bi);
		te.checkOutputs(this.getClass(), "testConnectionLabel", imageName);
	}
	
	@Test
	public void testConnectionLabelLeftBottom() throws IOException {
		Dimension2D size = new Dimension2D(1000, 400);
		BufferedImage bi = newImage(size);
		Graphics2D g2 = bi.createGraphics();	
		setHints(g2);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				testConnectionLabel(8+i*3, 8+j*3, (j*3)+18, VPos.DOWN, HPos.LEFT, i*200, j*80, g2, true);
			}	
		}
		TestingEngine te = new TestingEngine(getZipName());
		String imageName = "testLeftBottom.png";
		TestingEngine.renderToFile(getClass(), "testConnectionLabel", imageName, bi);
		te.checkOutputs(this.getClass(), "testConnectionLabel", imageName);
	}
	
	@Test
	public void testConnectionLabelRightTop() throws IOException {
		Dimension2D size = new Dimension2D(1000, 400);
		BufferedImage bi = newImage(size);
		Graphics2D g2 = bi.createGraphics();	
		setHints(g2);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				testConnectionLabel(8+i*3, 8+j*3, (j*3)+18, VPos.UP, HPos.RIGHT, i*200, j*80, g2, true);
			}	
		}
		TestingEngine te = new TestingEngine(getZipName());
		String imageName = "testRightTop.png";
		TestingEngine.renderToFile(getClass(), "testConnectionLabel", imageName, bi);
		te.checkOutputs(this.getClass(), "testConnectionLabel", imageName);
	}
	
	@Test
	public void testConnectionLabelRightBottom() throws IOException {
		Dimension2D size = new Dimension2D(1000, 400);
		BufferedImage bi = newImage(size);
		Graphics2D g2 = bi.createGraphics();	
		setHints(g2);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				testConnectionLabel(8+i*3, 8+j*3, (j*3)+18, VPos.DOWN, HPos.RIGHT, i*200, j*80, g2, true);
			}	
		}
		TestingEngine te = new TestingEngine(getZipName());
		String imageName = "testRightBottom.png";
		TestingEngine.renderToFile(getClass(), "testConnectionLabel", imageName, bi);
		te.checkOutputs(this.getClass(), "testConnectionLabel", imageName);
	}

/*	public void testConnectionLabel(final int lfs, final int sfs, final float ss, VPos vert, HPos horz, int x, int y, Graphics2D g2, boolean syms) throws IOException {
		
		g2.setPaint(Color.WHITE);
		Rectangle2D.Double r = new Rectangle2D.Double((double) x, (double) y, 200d, 80d);
		g2.fill(r);
		g2.setPaint(Color.RED);
		g2.draw(r);

		
		ConnectionLabelTextLineDisplayer disp = new ConnectionLabelTextLineDisplayer(g2, new BasicStylesheet() {
			
//			
//			@Override
//			public TextBoxStyle getConnectionLabelStyle() {
//					return new TextBoxStyle(
//							new DirectionalValues(2, 2, 2, 2), 
//							new DirectionalValues(2, 10, 8, 2),
//							new ShapeStyle(null, null,  new Color(0f, 0f, 0f, 0.1f), "", false, false),
//							new TextStyle(getFont(getPlainFont(),lfs), Color.BLACK, Justification.LEFT,getPlainFont()),
//							new TextStyle(getFont(getPlainFont(),lfs), Color.BLACK, Justification.LEFT, getPlainFont()),
//							false);
//			}
//		
//			@Override
//			public TextStyle getSymbolTextStyle() {
//				return new TextStyle(getFont(getPlainFont(), sfs), Color.WHITE, Justification.CENTER, getPlainFont());
//			}

			@Override
			public float getSymbolSize() {
				return (float) ss;
			}

			
		}) {

			@Override
			public PathConverter getPathConverter() {
				return new SVGPathConverter();
			}
			
			
		};
		
		
		TextLine tl =  new TextLine("Kellogs");
		if (syms) {
			tl.setSymbols(HelpMethods.createList(
				new Symbol("blah", 'A', SymbolShape.HEXAGON),
				new Symbol("blah", 'g', SymbolShape.CIRCLE),
				new Symbol("blah", 'c', SymbolShape.DIAMOND)));
		}
		
		
		
		RectangleRenderingInformation rri = new RectangleRenderingInformation();
		rri.setPosition(new Dimension2D(x,y));
		rri.setSize(new Dimension2D(200, 80));
		rri.setVerticalJustification(vert);
		rri.setHorizontalJustification(horz);
		disp.draw(tl, rri);
	
	}
	
	public void testContextLabel(final int lfs, final int sfs, final float ss, VPos vert, HPos horz, int x, int y, Graphics2D g2, String string, boolean syms) throws IOException {
		
		g2.setPaint(Color.WHITE);
		Rectangle2D.Double r = new Rectangle2D.Double((double) x, (double) y, 200d, 80d);
		g2.fill(r);
		g2.setPaint(Color.RED);
		g2.draw(r);

		
		ContextLabelTextLineDisplayer disp = new ContextLabelTextLineDisplayer(g2, new BasicStylesheet() {
			
//			@Override
//			public TextBoxStyle getContextLabelStyle() {
//				return new TextBoxStyle(
//						new DirectionalValues(2, 2, 2, 2), 
//						new DirectionalValues(2, 5, 2, 5),
//						new ShapeStyle(null, null,  new Color(0f, 0f, 0f, 0.1f), "", false, false),
//						new TextStyle(getFont(getPlainFont(), lfs), Color.BLACK, Justification.LEFT, getPlainFont()),
//						new TextStyle(getFont(getPlainFont(), lfs), Color.BLACK, Justification.LEFT, getPlainFont()),
//						false);
//			}
//			
//
//			@Override
//			public TextStyle getSymbolTextStyle() {
//				return new TextStyle(getFont(getPlainFont(), sfs), Color.WHITE, Justification.CENTER, getPlainFont());
//			}

			@Override
			public float getSymbolSize() {
				return (float) ss;
			}
			
			
		});
		
		
		TextLine tl =  new TextLine(string);
		if (syms) {
			tl.setSymbols(HelpMethods.createList(
					new Symbol("blah", 'A', SymbolShape.HEXAGON),
					new Symbol("blah", 'g', SymbolShape.CIRCLE),
					new Symbol("blah", 'c', SymbolShape.DIAMOND)));
		}
		
		RectangleRenderingInformation rri = new RectangleRenderingInformation();
		rri.setPosition(new Dimension2D(x,y));
		rri.setSize(new Dimension2D(200, 80));
		rri.setVerticalJustification(vert);
		rri.setHorizontalJustification(horz);
		disp.draw(tl, rri);
	
	}
	
	*/
}
