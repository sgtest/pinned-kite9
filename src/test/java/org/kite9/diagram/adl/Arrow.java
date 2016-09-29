package org.kite9.diagram.adl;

import org.kite9.diagram.xml.ADLDocument;
import org.kite9.diagram.xml.AbstractStyleableXMLElement;
import org.kite9.diagram.xml.XMLElement;
import org.w3c.dom.Node;

/**
 * This class models the black body of the arrow, which will have links 
 * entering and leaving it.
 * 
 * @author moffatr
 *
 */

public class Arrow extends AbstractStyleableXMLElement {

	private static final long serialVersionUID = 5054715961820271315L;

	public Arrow() {
		this.tagName = "arrow";
	}
		
	public XMLElement getLabel() {
		return getProperty("label");
	}

	public void setLabel(XMLElement name) {
		replaceProperty("label", name);
	}

	
	public Arrow(String id, String label, ADLDocument doc) {
		super(id, "arrow", doc);

		if (label != null) {
			setLabel(new TextLine(null, "label", label, null, doc));
		}
		
	}
	
	public Arrow(String id, String label) {
		this(id, label, TESTING_DOCUMENT);
	}

	public Arrow(String label) {
		this(label, label);
	}

	public boolean hasDimension() {
		return true;
	}

	public String toString() {
		return "[A:"+getID()+"]";
	}
	
	@Override
	protected Node newNode() {
		return new Arrow();
	}

}