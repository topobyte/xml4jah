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

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import de.topobyte.hrx.HrxException;
import de.topobyte.hrx.HrxFile;
import de.topobyte.hrx.HrxReader;
import de.topobyte.xml4jah.core.AttributeOrder;
import de.topobyte.xml4jah.core.DocumentWriterConfig;
import de.topobyte.xml4jah.dom.DocumentWriter;

public class TestXmlStreamDocumentsBuilder2
{

	@Test
	public void test() throws IOException, HrxException, XMLStreamException,
			ParserConfigurationException
	{
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("books/v2/data.hrx");
		List<HrxFile> files = new HrxReader().read(new InputStreamReader(is));

		HrxFile input = files.get(0);
		String content = input.getContent();

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = xmlInputFactory
				.createXMLStreamReader(new StringReader(content));

		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setAttributeOrder("author",
				new AttributeOrder(Arrays.asList("wikidata", "name")));
		config.setAttributeOrder("book",
				new AttributeOrder(Arrays.asList("year", "wikidata")));
		DocumentWriter writer = new DocumentWriter(config);

		int n = 1;
		while (reader.hasNext()) {
			int type = reader.next();
			if (type == START_ELEMENT) {
				String local = reader.getLocalName();
				if (local.equals("author")) {
					test(reader, writer, files.get(n++).getContent());
				}
			}
		}
	}

	private void test(XMLStreamReader reader, DocumentWriter writer,
			String expected)
			throws ParserConfigurationException, XMLStreamException, IOException
	{
		XmlStreamDocumentBuilder parser = new XmlStreamDocumentBuilder();
		Document doc = parser.build(reader);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		writer.write(doc, baos);
		String result = new String(baos.toByteArray());

		Assert.assertEquals(expected, result);
	}

}
