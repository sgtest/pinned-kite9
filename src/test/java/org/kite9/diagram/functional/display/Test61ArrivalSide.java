package org.kite9.diagram.functional.display;

import java.util.Collections;

import org.junit.Test;
import org.kite9.diagram.AbstractDisplayFunctionalTest;
import org.kite9.diagram.AbstractLayoutFunctionalTest;
import org.kite9.diagram.adl.AbstractMutableXMLElement;
import org.kite9.diagram.adl.Context;
import org.kite9.diagram.adl.DiagramKite9XMLElement;
import org.kite9.diagram.adl.Link;
import org.kite9.diagram.dom.elements.GenericKite9XMLElement;
import org.kite9.diagram.model.position.Direction;
import org.kite9.diagram.model.position.Layout;
import org.kite9.framework.common.HelpMethods;

public class Test61ArrivalSide extends AbstractDisplayFunctionalTest {

    @SuppressWarnings("unchecked")
	@Test
    public void test_60_1_LinkedDifferentDirection() throws Exception {
    	Context i1 = new Context("i1", Collections.EMPTY_LIST, true, null, Layout.DOWN);
    	Context i2 = new Context("i2", Collections.EMPTY_LIST, true, null, Layout.DOWN);
    	Context i3 = new Context("i3", Collections.EMPTY_LIST, true, null, Layout.DOWN);
    	Context i4 = new Context("i4", Collections.EMPTY_LIST, true, null, Layout.DOWN);
    	
    	Context outer = new Context("outer", HelpMethods.listOf(i1, i2, i3, i4), true, null, null);
    	Link l1 = new Link(i1, i2, null, null, null, null, Direction.DOWN);
    	Link l2 = new Link(i4, i3,null, null, null, null, Direction.RIGHT);
    	
    	AbstractMutableXMLElement fromT = l1.getProperty("from");
    	fromT.setAttribute("style", "kite9-arrival-side: up; ");
    	
    	AbstractMutableXMLElement toT = l1.getProperty("from");
    	toT.setAttribute("style", "kite9-arrival-side: up; ");
    	
    	DiagramKite9XMLElement d= new DiagramKite9XMLElement(HelpMethods.listOf(outer), null);
    	renderDiagram(d);
    }
    

}