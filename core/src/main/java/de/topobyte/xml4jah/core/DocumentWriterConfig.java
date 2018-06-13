// Copyright 2018 Sebastian Kuerten
//
// This file is part of xml4jah.
//
// xml4jah is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// xml4jah is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with xml4jah. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.xml4jah.core;

import java.util.HashMap;
import java.util.Map;

public class DocumentWriterConfig
{

	private String indent = "  ";
	private boolean withDeclaration = true;
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

	public boolean isWithDeclaration()
	{
		return withDeclaration;
	}

	public void setWithDeclaration(boolean withDeclaration)
	{
		this.withDeclaration = withDeclaration;
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
