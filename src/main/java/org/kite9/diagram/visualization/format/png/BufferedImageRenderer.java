package org.kite9.diagram.visualization.format.png;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.kite9.diagram.adl.Diagram;
import org.kite9.diagram.position.Dimension2D;
import org.kite9.diagram.visualization.format.AbstractScalingGraphicsSourceRenderer;

public class BufferedImageRenderer extends AbstractScalingGraphicsSourceRenderer<BufferedImage> {

	public BufferedImageRenderer(Integer width, Integer height) {
		super(width, height);
	}
	
	public BufferedImageRenderer() {
		this(null, null);
	}

	BufferedImage bi;
	Graphics2D g2;

	public BufferedImage render(Diagram something) {
		Dimension2D out = size(something);
		dea.initialize(this, out);
		drawDiagramElements(something);
		dea.finish();
		bi.flush();
		g2.dispose();
		return bi;
	}

	public Graphics2D getGraphics(int layer, float transparency, float scale, Dimension2D imageSize, Dimension2D diagramSize) {
		
		if ((bi==null) || (bi.getWidth() != imageSize.getWidth()) ||  (bi.getHeight() != imageSize.getHeight())) {
			bi = new BufferedImage((int) imageSize.getWidth(), (int) imageSize.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			g2 = bi.createGraphics();	
			setRenderingHints(g2);
			applyScaleAndTranslate(g2, scale, imageSize, diagramSize);
		}

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,transparency));
		
		return g2;
	}
}
