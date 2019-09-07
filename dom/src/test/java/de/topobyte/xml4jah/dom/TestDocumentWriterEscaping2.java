// Copyright 2019 Sebastian Kuerten
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

package de.topobyte.xml4jah.dom;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.topobyte.xml4jah.core.AttributeOrder;
import de.topobyte.xml4jah.core.DocumentWriterConfig;

public class TestDocumentWriterEscaping2
{

	@Test
	public void testRewrite()
			throws IOException, ParserConfigurationException, SAXException
	{
		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setAttributeOrder("book",
				new AttributeOrder(Arrays.asList("year", "wikidata", "title")));
		config.setPreserveEmptyLines(true);

		Util.test("escaping/v2/source.xml", document(), config);
	}

	private Document document()
			throws IOException, SAXException, ParserConfigurationException
	{
		return Util.document("escaping/v2/source.xml");
	}

}
