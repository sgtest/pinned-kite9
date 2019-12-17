package org.kite9.diagram.dom.processors.xpath;

import org.kite9.diagram.dom.processors.xpath.ValueReplacingProcessor.ValueReplacer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.xpath.XPathEvaluator;
import org.w3c.dom.xpath.XPathResult;

public class NodeValueReplacer implements ValueReplacer {
	
	Node from;
	Document d;

	public NodeValueReplacer(Node from) {
		super();
		this.from = from;
		this.d = from.getOwnerDocument();
	}

	@Override
	public String getReplacementStringValue(String xpath, Node position) {
		XPathResult out = (XPathResult) ((XPathEvaluator) d).evaluate(xpath, from, null, XPathResult.STRING_TYPE, null);
		return out.getStringValue();
	}

	@Override
	public XPathResult getReplacementXML(String xpath, short type, Node postion) {
		XPathResult out = (XPathResult) ((XPathEvaluator) d).evaluate(xpath, from, null, type, null);
		return out;
	}
}
