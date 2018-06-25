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

package de.topobyte.xml4jah.dom;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.topobyte.xml4jah.core.AttributeOrder;
import de.topobyte.xml4jah.core.DocumentWriterConfig;

public class TestDocumentWriter5
{

	@Test
	public void testRewrite()
			throws IOException, ParserConfigurationException, SAXException
	{
		test("adams/v5/no-newlines.xml",
				Arrays.asList("year", "wikidata", "title"));
	}

	private void test(String resource, List<String> order)
			throws IOException, SAXException, ParserConfigurationException
	{
		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setAttributeOrder("book", new AttributeOrder(order));

		Util.test(resource, document(), config);
	}

	private Document document()
			throws IOException, SAXException, ParserConfigurationException
	{
		return Util.document("adams/v5/source.xml");
	}

}
