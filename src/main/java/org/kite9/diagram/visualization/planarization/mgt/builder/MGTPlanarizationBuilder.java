package org.kite9.diagram.visualization.planarization.mgt.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.kite9.diagram.adl.Arrow;
import org.kite9.diagram.adl.Diagram;
import org.kite9.diagram.adl.Glyph;
import org.kite9.diagram.adl.TextLine;
import org.kite9.diagram.common.elements.RoutingInfo;
import org.kite9.diagram.common.elements.Vertex;
import org.kite9.diagram.primitives.BiDirectional;
import org.kite9.diagram.primitives.Connected;
import org.kite9.diagram.primitives.Contained;
import org.kite9.diagram.primitives.Container;
import org.kite9.diagram.primitives.DiagramElement;
import org.kite9.diagram.primitives.StyledText;
import org.kite9.diagram.visualization.planarization.mapping.ElementMapper;
import org.kite9.diagram.visualization.planarization.mgt.MGTPlanarization;
import org.kite9.diagram.visualization.planarization.mgt.MGTPlanarizationImpl;
import org.kite9.diagram.visualization.planarization.rhd.RHDPlanarizationBuilder;
import org.kite9.framework.logging.Kite9Log;
import org.kite9.framework.logging.Logable;
import org.kite9.framework.logging.Table;

/**
 * This follows the general GT approach to producing a maximal planar subgraph
 * by introducing edges above and below the line of the planarization, and as
 * many as possible.
 * 
 * @author moffatr
 * 
 */
public abstract class MGTPlanarizationBuilder extends RHDPlanarizationBuilder implements Logable {
	
	public MGTPlanarizationBuilder(ElementMapper em) {
		super(em);
	}
	
	protected Kite9Log log = new Kite9Log(this);
		
	public static boolean debug = false;
	
	protected MGTPlanarizationImpl buildPlanarization(Diagram d, List<Vertex> vertexOrder, Collection<BiDirectional<Connected>> initialUninsertedConnections, Map<Container, List<Contained>> sortedContainerContents) {
		MGTPlanarizationImpl p = new MGTPlanarizationImpl(d, vertexOrder, initialUninsertedConnections, sortedContainerContents);
		logPlanarEmbeddingDetails(p, log);
		getRoutableReader().initRoutableOrdering(vertexOrder);
		completeEmbedding(p);
		log.send(log.go() ? null : "Initial Planar Embedding: \n" + p.toString());
		if (debug) 
			addDebugInfo(vertexOrder);
		return p;
	}

	protected void addDebugInfo(List<Vertex> vo) {
		for (Vertex vertex : vo) {
			DiagramElement originalUnderlying = vertex.getOriginalUnderlying();
			if (originalUnderlying instanceof Glyph) {
				String text = "X="+vertex.getRoutingInfo().outputX()+"\nY="+vertex.getRoutingInfo().outputY();
				((Glyph) originalUnderlying).getText().add(new TextLine(text));
			} else if (originalUnderlying instanceof Arrow) {
				String text = "X="+vertex.getRoutingInfo().outputX()+"\nY="+vertex.getRoutingInfo().outputY();
				StyledText orig = ((Arrow) originalUnderlying).getLabel();
				String origText = orig == null ? "" : orig.getText();
				((Arrow) originalUnderlying).setLabel(new StyledText(origText+"\n"+text));
			}
		}
	}

	public static void logPlanarEmbeddingDetails(MGTPlanarization pln, Kite9Log log) {
		Table t = new Table();
		List<Vertex> vertexOrder = pln.getVertexOrder();
		int size = vertexOrder.size();
		
		List<String> xPositions = new ArrayList<String>(size);
		List<String> yPositions = new ArrayList<String>(size);
		List<String> index = new ArrayList<String>(size);
		
		for (int i = 0; i < size; i++) {
			RoutingInfo routingInfo = vertexOrder.get(i).getRoutingInfo();
			xPositions.add(routingInfo == null ? "" : routingInfo.outputX());
			yPositions.add(routingInfo == null ? "" : routingInfo.outputY());
			index.add(""+i);
			
		}
		
		t.addRow(index);
		t.addRow(vertexOrder);
		t.addRow(xPositions);
		t.addRow(yPositions);
		
		
		log.send(log.go() ? null : "Vertex Notional Positions: \n",t);
	}

	/**
	 * This method allows you to do any post-processing of the planarization.
	 */
	protected void completeEmbedding(MGTPlanarization p) {
		processConnections(p);
	}

	protected abstract void processConnections(MGTPlanarization p);

	@Override
	public String getPrefix() {
		return "GTPB";
	}

	@Override
	public boolean isLoggingEnabled() {
		return true;
	}

}
