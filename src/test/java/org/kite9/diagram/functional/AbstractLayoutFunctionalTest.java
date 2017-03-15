package org.kite9.diagram.functional;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.kite9.diagram.adl.Connection;
import org.kite9.diagram.adl.DiagramElement;
import org.kite9.diagram.adl.Link;
import org.kite9.diagram.functional.layout.TestingEngine;
import org.kite9.diagram.functional.layout.TestingEngine.Checks;
import org.kite9.diagram.position.RouteRenderingInformation;
import org.kite9.diagram.visitors.DiagramElementVisitor;
import org.kite9.diagram.visitors.VisitorAction;
import org.kite9.diagram.visualization.batik.bridge.Kite9DiagramBridge;
import org.kite9.diagram.visualization.format.pos.DiagramChecker;
import org.kite9.diagram.visualization.pipeline.full.AbstractArrangementPipeline;
import org.kite9.diagram.xml.DiagramXMLElement;
import org.kite9.framework.common.RepositoryHelp;
import org.kite9.framework.common.StackHelp;
import org.kite9.framework.common.TestingHelp;
import org.kite9.framework.logging.Kite9Log;
import org.kite9.framework.logging.LogicException;
import org.kite9.framework.serialization.XMLHelper;

public class AbstractLayoutFunctionalTest extends AbstractFunctionalTest {
	

	protected DiagramXMLElement renderDiagram(DiagramXMLElement d) throws Exception {
		String xml = new XMLHelper().toXML(d);
		return renderDiagram(xml);
	}
	
	protected DiagramXMLElement renderDiagram(String xml) throws Exception {
		String full = addSVGFurniture(xml);
		transcodePNG(full);
		transcodeSVG(full);
		DiagramXMLElement lastDiagram = Kite9DiagramBridge.lastDiagram;
		AbstractArrangementPipeline lastPipeline = Kite9DiagramBridge.lastPipeline;
		boolean addressed = isAddressed();
		new TestingEngine().testDiagram(lastDiagram, this.getClass(), getTestMethod(), checks(), addressed, lastPipeline);
		return lastDiagram;
	}

	private boolean isAddressed() {
		Method m = StackHelp.getAnnotatedMethod(Test.class);
		return !(m.isAnnotationPresent(NotAddressed.class));
	}

	private String getTestMethod() {
		return StackHelp.getAnnotatedMethod(org.junit.Test.class).getName();
	}

	protected File getOutputFile(String ending) {
		Method m = StackHelp.getAnnotatedMethod(Test.class);
		Class<?> theTest = m.getDeclaringClass();
		File f = TestingHelp.prepareFileName(theTest, m.getName(), m.getName()+ending);
		return f;
	}

	protected Checks checks() {
		Checks out = new Checks();
		out.checkEdgeDirections = checkEdgeDirections();
		out.checkLabelOcclusion = checkLabelOcclusion();
		out.checkLayout = checkLayout();
		out.checkNoContradictions = checkNoContradictions();
		out.checkNoHops = checkNoHops();
		out.everythingStraight = checkEverythingStraight();
		return out;
	}
	
	protected boolean checkLabelOcclusion() {
		return false;
	}
	
	protected boolean checkDiagramSize() {
		return false;
	}
	
	protected boolean checkEdgeDirections() {
		return true;
	}
	
	protected boolean checkNoHops() {
		return true;
	}
	
	protected boolean checkEverythingStraight() {
		return true;
	}
	
	protected boolean checkLayout() {
		return true;
	}
	
	protected boolean checkNoContradictions() {
		return true;
	}
	
	static boolean firstRun = true;
	
	
	@Before
	public void setLogging() {
		Kite9Log.setLogging(true);
		
		// if we are running more than one test, then there's no point in logging.
		if (firstRun) {
			firstRun = false;
		} else {
			Kite9Log.setLogging(false);
		}
	}
	

	
	protected DiagramElement getById(final String id, DiagramXMLElement d) {
		DiagramElementVisitor vis = new DiagramElementVisitor();
		final DiagramElement[] found = { null };
		vis.visit(d.getDiagramElement(), new VisitorAction() {
			
			@Override
			public void visit(DiagramElement de) {
				if (de.getID().equals(id)) {
						found[0] = de;
				}
			}
		});
		
		return found[0];
	}

	protected void mustTurn(DiagramXMLElement d, Link l) {
		DiagramChecker.checkConnnectionElements(d, new DiagramChecker.ConnectionAction() {
	
			@Override
			public void action(RouteRenderingInformation rri, Object d, Connection c) {
				if (l == c) {
					if (d != DiagramChecker.MULTIPLE_DIRECTIONS) {
						throw new LogicException("Should be turning");
					}
				}
			}
		});
	}

	protected void mustContradict(DiagramXMLElement diag, Link l) {
		DiagramChecker.checkConnnectionElements(diag, new DiagramChecker.ConnectionAction() {
			
			@Override
			public void action(RouteRenderingInformation rri, Object d, Connection c) {
				if (c == l) {
					if (d != DiagramChecker.SET_CONTRADICTING) {
						throw new LogicException("Should be contradicting");
					}
				}
			}
		});
	}
	
	public void generate(String name) throws Exception {
		InputStream is = this.getClass().getResourceAsStream("/org/kite9/diagram/xml/"+name);
		InputStreamReader isr = new InputStreamReader(is);
		StringWriter sw = new StringWriter();
		RepositoryHelp.streamCopy(isr, sw, true);
		renderDiagram(sw.toString());
	}
}