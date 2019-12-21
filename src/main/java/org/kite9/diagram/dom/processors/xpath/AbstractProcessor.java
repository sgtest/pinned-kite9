package org.kite9.diagram.dom.processors.xpath;

import org.kite9.diagram.dom.processors.XMLProcessor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public abstract class AbstractProcessor implements XMLProcessor {

	public AbstractProcessor() {
		super();
	}

	public Node processContents(Node n, Node inside) {
		if (n instanceof Element) {
			Element out = processTag((Element) n);
			
			if (inside != null) {
				System.out.println("Appending "+n);
				inside.appendChild(out);
			}
			
			NodeList contents = n.getChildNodes();
			mergeTextNodes(contents);
			processContents(contents, out);
			return out;
		} else if (n instanceof Text) {
			Text out = processText((Text) n);
			if (inside != null) {
				inside.appendChild(out);
			}
			return out;
		} else {
			NodeList contents = n.getChildNodes();
			processContents(contents, inside);
			return inside;
		}
	}
	
	protected abstract Element processTag(Element n);

	protected abstract Text processText(Text n);

	protected void processContents(NodeList contents, Node inside) {
		for (int i = 0; i < contents.getLength(); i++) {
			Node n = contents.item(i);
			processContents(n, inside);
		}
	}
	
	protected void mergeTextNodes(NodeList nodeList) {
		Text lastTextNode = null;
		int i = 0;
		while (i < nodeList.getLength()) {
			Node n = nodeList.item(i);
			if (n instanceof Text) {
				if (lastTextNode != null) {
					lastTextNode.setData(lastTextNode.getData() + ((Text)n).getData());
					n.getParentNode().removeChild(n);
				} else {
					i++;
				}
			} else {
				lastTextNode = null;
				i++;
			}
		}
	}

}