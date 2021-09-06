package org.kite9.diagram.functional.display;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.kite9.diagram.AbstractDisplayFunctionalTest;
import org.kite9.diagram.adl.*;
import org.kite9.diagram.common.HelpMethods;
import org.kite9.diagram.dom.css.CSSConstants;
import org.kite9.diagram.functional.TestingEngine;
import org.kite9.diagram.model.position.Direction;
import org.kite9.diagram.model.position.Layout;
import org.kite9.diagram.model.style.BorderTraversal;
import org.w3c.dom.Element;

import java.util.Arrays;

public class Test61PortsAndAlignment extends AbstractDisplayFunctionalTest {

	/**
	 * Disabling some checks.  61_12 edges don't run straight anymore, however it's correct behaviour.
	 * @return
	 */
	@Override
	protected TestingEngine.Checks checks() {
		TestingEngine.Checks out = new TestingEngine.Checks();
		out.everythingStraight = false;
		out.checkEdgeDirections = false;
		out.checkMidConnection = false;
		return out;
	}

	@Test
	public void test_61_1_PortPlacement() throws Exception {
		Glyph one = createGlyph("One");
		addSixPorts(one);

		Context i1 = new Context("i1", Arrays.asList( one ), true, null, Layout.DOWN);
		addSixPorts(i1);

		DiagramKite9XMLElement d= new DiagramKite9XMLElement(HelpMethods.listOf(i1), null);
		renderDiagram(d);
	}

	private void addSixPorts(Element e) {
		e.appendChild(new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.TOP, "10px"));
		e.appendChild(new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.TOP, "-10px"));
		e.appendChild(new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.BOTTOM, "10%"));
		e.appendChild(new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.BOTTOM, "90%"));
		e.appendChild(new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.LEFT, "50%"));
		e.appendChild(new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "50%"));
	}

	@Test
	public void test_61_2_SimpleLinkToPort() throws Exception {
		Glyph one = createGlyph("One");
		BasicSocket oneSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "130px");
		one.appendChild(oneSocket);

		Glyph two = createGlyph("Two");
		BasicSocket twoSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.LEFT, "10px");
		two.appendChild(twoSocket);
		Link l4 = new Link(oneSocket, twoSocket);

		DiagramKite9XMLElement d= new DiagramKite9XMLElement("dia", HelpMethods.listOf(two, one), Layout.DOWN, null);
		renderDiagram(d);
	}

	@Test
	// this doesn't look great, but at least it tests labels.
	public void test_61_3_MultipleUndirectedLinksToPortLabelled() throws Exception {
		Glyph one = createGlyph("One");
		BasicSocket oneSocket = new BasicSocket("sock1", BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "30px");
		one.appendChild(oneSocket);

		Glyph two = createGlyph("Two");
		Glyph three = createGlyph("Three");
		Link l4 = new Link(oneSocket, two, null, new TextLabel("from end 2"), null, new TextLabel("to end 2"), null);
		Link l3 = new Link(oneSocket, three, null, new TextLabel("from end"), null, new TextLabel("to end"), null);

		DiagramKite9XMLElement d= new DiagramKite9XMLElement( HelpMethods.listOf(one, two, three), null);
		renderDiagram(d);
	}

	@Test
	public void test_61_4_MultipleUndirectedLinksToPortEven() throws Exception {
		Glyph one = createGlyph("One");
		one.setAttribute("style", CSSConstants.TRAVERSAL_PROPERTY+": "+ BorderTraversal.PREVENT+";");
		BasicSocket oneSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "30px");
		one.appendChild(oneSocket);

		Glyph two = createGlyph("Two");
		Glyph three = createGlyph("Three");
		Glyph four = createGlyph("Four");
		Glyph five = createGlyph("Five");

		Context i1 = new Context("i1", Arrays.asList( two, three, four, five ), true, null, Layout.DOWN);

		Link l4 = new Link(oneSocket, two);
		Link l3 = new Link(oneSocket, three, null, null, null, null, null);
		Link l5 = new Link(oneSocket, four);
		Link l6 = new Link(oneSocket, five);

		DiagramKite9XMLElement d= new DiagramKite9XMLElement( HelpMethods.listOf(one, i1), null);
		renderDiagram(d);
	}

	@Test
	public void test_61_5_MultipleUndirectedLinksToPortOdd() throws Exception {
		Glyph one = createGlyph("One");
		one.setAttribute("style", CSSConstants.TRAVERSAL_PROPERTY+": "+ BorderTraversal.PREVENT+";");
		BasicSocket oneSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.TOP, "50%");
		BasicSocket twoSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.LEFT, "50%");
		one.appendChild(oneSocket);
		one.appendChild(twoSocket);

		Glyph two = createGlyph("Two");
		Glyph three = createGlyph("3");
		Glyph four = createGlyph("Four");
		Glyph five = createGlyph("Five");
		Glyph six = createGlyph("Six");

		Context i1 = new Context("i1", Arrays.asList( two, three, four, five, six), true, null, Layout.RIGHT);

		Link l4 = new Link(oneSocket, two);
		Link l3 = new Link(oneSocket, three, null, null, null, null, null);
		Link l5 = new Link(oneSocket, four);
		Link l6 = new Link(twoSocket, five);
		Link l7 = new Link(oneSocket, six);

		DiagramKite9XMLElement d= new DiagramKite9XMLElement( HelpMethods.listOf(one, i1), null);
		renderDiagram(d);
	}

	@Test
	public void test_61_6_PortAndNonPortFanning() throws Exception{
		Glyph one = createGlyph("One");
		one.setAttribute("style", CSSConstants.TRAVERSAL_PROPERTY+": "+ BorderTraversal.PREVENT+";");
		BasicSocket oneSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "50%");
		one.appendChild(oneSocket);

		Glyph two = createGlyph("Two");
		Glyph three = createGlyph("Three");
		Glyph four = createGlyph("Four");
		Glyph five = createGlyph("Five");

		Context i1 = new Context("i1", Arrays.asList( two, three, four, five ), true, null, Layout.DOWN);

		// two links to the first guy
		Link l4 = new Link(oneSocket, two);
		Link l4_2 = new Link(oneSocket, two);

		Link l3 = new Link(oneSocket, three, null, null, null, null, null);

		// links not on port
		Link l5 = new Link(one, four);
		Link l6 = new Link(one, five);

		DiagramKite9XMLElement d= new DiagramKite9XMLElement( HelpMethods.listOf(one, i1), null);
		renderDiagram(d);
	}

	@Test
	public void test_61_7_DirectedWithPorts() throws Exception {
		Glyph one = createGlyph("One");
		one.setAttribute("style", CSSConstants.TRAVERSAL_PROPERTY+": "+ BorderTraversal.PREVENT+";");

		Glyph two = createGlyph("Two");
		Glyph three = createGlyph("Three");

		Context i1 = new Context("i1", Arrays.asList( two, three), true, null, Layout.RIGHT);
		BasicSocket i1Socket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "50%");
		i1.appendChild(i1Socket);

		new Link(i1Socket, one);

		DiagramKite9XMLElement d= new DiagramKite9XMLElement( HelpMethods.listOf(one, i1), null);
		renderDiagram(d);
	}

	@Test
	public void test_61_8_AlignmentAndPorts() throws Exception {
		Glyph one = createGlyph("One");
		one.setAttribute("style", CSSConstants.TRAVERSAL_PROPERTY+": "+ BorderTraversal.PREVENT+";");
		BasicSocket oneSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "50%");
		one.appendChild(oneSocket);

		Glyph two = createGlyph("Two");

		Glyph three = createGlyph("Three");
		BasicSocket threeSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.LEFT, "50%");
		three.appendChild(threeSocket);


		Context i1 = new Context("i1", Arrays.asList(one, two, three), true, null, Layout.RIGHT);

		new Link(oneSocket, threeSocket);
		new Link(oneSocket, two, null, null, null, null, Direction.RIGHT);

		DiagramKite9XMLElement d= new DiagramKite9XMLElement( HelpMethods.listOf(i1), null);
		renderDiagram(d);
	}

	@Test
	public void test_61_9_OffsetPortsAndMiddles() throws Exception {
		Glyph one = createGlyph("One");
		one.setAttribute("style", CSSConstants.TRAVERSAL_PROPERTY+": "+ BorderTraversal.PREVENT+";");
		BasicSocket oneSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "30px");
		one.appendChild(oneSocket);
		BasicSocket twoSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "130px");
		one.appendChild(twoSocket);
		BasicSocket threeSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.LEFT, "75%");
		one.appendChild(threeSocket);

		Glyph two = createGlyph("Two");
		Glyph three = createGlyph("Three");
		Glyph four = createGlyph("Four");

		Link l4 = new Link(oneSocket, two);
		Link l3 = new Link(three, twoSocket);
		Link l5 = new Link(four, threeSocket);

		DiagramKite9XMLElement d= new DiagramKite9XMLElement( HelpMethods.listOf(two, one, three, four), null);
		renderDiagram(d);
	}

	@Test
	public void test_61_10_Alignments() throws Exception {
		String alignmentStyle = CSSConstants.HORIZONTAL_ALIGN_POSITION+ ": -5px; "+
				CSSConstants. VERTICAL_ALIGN_POSITION+": 10px; "+
				CSSConstants.LINK_INSET+": 0px; ";
		Glyph one = createStyledGlyph("One", alignmentStyle);
		Glyph two = createStyledGlyph("Two", alignmentStyle);
		Glyph three = createStyledGlyph("Three", alignmentStyle);
		Glyph four = createStyledGlyph("Four", alignmentStyle);

		Link l4 = new Link(one, two);
		Link l3 = new Link(one, three, null, null, null, null, Direction.RIGHT);
		Link l5 = new Link(one, four);
		TurnLink l6 = new TurnLink(two, four);

		DiagramKite9XMLElement d= new DiagramKite9XMLElement("dia", HelpMethods.listOf(two, one, three, four), null);
		renderDiagram(d);
	}

	@NotNull
	private Glyph createGlyph(String one) {
		return new Glyph("Stereo", one, null, null);
	}

	private Glyph createStyledGlyph(String name, String style) {
		Glyph g = createGlyph(name);
		g.setAttribute("style", style);
		return g;
	}


	@Test
	public void test_61_11_MultiplePortsDirectedLinks() throws Exception {
		Glyph one = createGlyph("One");
		one.setAttribute("style", CSSConstants.TRAVERSAL_PROPERTY+": "+ BorderTraversal.PREVENT+";");
		BasicSocket oneSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "25%");
		one.appendChild(oneSocket);
		BasicSocket twoSocket = new BasicSocket(BasicSocket.createID(), BasicSocket.TESTING_DOCUMENT, CSSConstants.RIGHT, "75%");
		one.appendChild(twoSocket);

		Glyph two = createGlyph("Two");
		Glyph three = createGlyph("Three");
		Glyph four = createGlyph("Four");


		new Link(oneSocket, two, null, null, null, null, Direction.RIGHT);
		new Link(twoSocket, three, null, null, null, null, Direction.RIGHT);
		new Link(one, four, null, null, null, null, Direction.RIGHT);


		DiagramKite9XMLElement d= new DiagramKite9XMLElement( HelpMethods.listOf(one, two, three, four), null);
		renderDiagram(d);
	}

}