package de.topobyte.xml4jah.core;

import java.util.HashMap;
import java.util.Map;

public class DocumentWriterConfig
{

	private String indent = "  ";
	private boolean withEndingNewline = true;

	private Map<String, AttributeOrder> attributeOrders = new HashMap<>();

	public String getIndent()
	{
		return indent;
	}

	public void setIndent(String indent)
	{
		this.indent = indent;
	}

	public boolean isWithEndingNewline()
	{
		return withEndingNewline;
	}

	public void setWithEndingNewline(boolean withEndingNewline)
	{
		this.withEndingNewline = withEndingNewline;
	}

	public AttributeOrder getAttributeOrder(String nodeName)
	{
		return attributeOrders.get(nodeName);
	}

	// TODO: this should support exact positions in a chain of nodes, not just
	// named nodes independent of their position in the document.
	public void setAttributeOrder(String nodeName, AttributeOrder order)
	{
		attributeOrders.put(nodeName, order);
	}

}
