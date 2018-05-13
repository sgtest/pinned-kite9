package org.kite9.diagram.dom.processors.xpath;

import org.kite9.diagram.dom.elements.ADLDocument;
import org.kite9.diagram.dom.processors.xpath.ValueReplacingProcessor.ValueReplacer;
import org.w3c.dom.Node;
import org.w3c.dom.xpath.XPathEvaluator;
import org.w3c.dom.xpath.XPathNSResolver;
import org.w3c.dom.xpath.XPathResult;

public class XPathValueReplacer implements ValueReplacer {
	
    final XPathEvaluator xpathEvaluator;
    final XPathNSResolver resolver;
     
    public XPathValueReplacer(ADLDocument in) {
    		this.xpathEvaluator = in;
    		this.resolver = null;
    }


	@Override
	public String getReplacementValue(String xpath, Node at) {
		XPathResult out = (XPathResult) xpathEvaluator.evaluate(xpath, at, resolver, XPathResult.STRING_TYPE, null);
		return out.getStringValue();
	}

}
