// Copyright 2020 Sebastian Kuerten
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

package de.topobyte.xml4jah.stax;

import static javax.xml.stream.XMLStreamConstants.CHARACTERS;
import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FragmentParser
{

	private DocumentBuilder documentBuilder;

	public FragmentParser() throws ParserConfigurationException
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
	}

	public Document build(XMLStreamReader reader)
			throws FileNotFoundException, XMLStreamException
	{
		Document doc = documentBuilder.newDocument();

		Element rootElement = element(reader, doc);
		doc.appendChild(rootElement);

		build(reader, doc, rootElement);

		return doc;
	}

	private Element element(XMLStreamReader reader, Document doc)
	{
		Element element = doc.createElement(reader.getLocalName());
		for (int attributeIndex = 0; attributeIndex < reader
				.getAttributeCount(); attributeIndex++) {
			element.setAttribute(reader.getAttributeLocalName(attributeIndex),
					reader.getAttributeValue(attributeIndex));
		}
		return element;
	}

	private void build(XMLStreamReader reader, Document doc, Element root)
			throws XMLStreamException
	{
		Element currentElement = root;

		int level = 0;
		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			case START_ELEMENT:
				Element element = element(reader, doc);
				currentElement.appendChild(element);
				currentElement = element;
				level += 1;
				continue;
			case CHARACTERS:
				currentElement
						.appendChild(doc.createTextNode(reader.getText()));
				continue;
			case END_ELEMENT:
				level -= 1;
				if (level < 0) {
					return;
				}
				currentElement = (Element) currentElement.getParentNode();
			}
		}
	}

}
