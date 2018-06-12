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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.topobyte.xml4jah.core.AttributeOrder;
import de.topobyte.xml4jah.core.DocumentWriterConfig;

public class TestDocumentWriter
{

	@Test
	public void testParseBooks()
			throws IOException, ParserConfigurationException, SAXException
	{
		List<Book> books = books();
		Assert.assertEquals(11, books.size());
	}

	@Test
	public void testRewrite()
			throws IOException, ParserConfigurationException, SAXException
	{
		String text = documentAsText("adams/v1/source.xml");
		Document doc = document();

		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setAttributeOrder("book",
				new AttributeOrder(Arrays.asList("year", "wikidata", "title")));

		DocumentWriter writer = new DocumentWriter(config);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		writer.write(doc, baos);

		String output = baos.toString();
		Assert.assertEquals(text, output);
	}

	@Test
	public void testRewriteNoEndAtNewline()
			throws IOException, ParserConfigurationException, SAXException
	{
		String text = documentAsText("adams/v1/no-ending-newline.xml");
		Document doc = document();

		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setWithEndingNewline(false);
		config.setAttributeOrder("book",
				new AttributeOrder(Arrays.asList("year", "wikidata", "title")));

		DocumentWriter writer = new DocumentWriter(config);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		writer.write(doc, baos);

		String output = baos.toString();
		Assert.assertEquals(text, output);
	}

	@Test
	public void testNaturalOrder()
			throws IOException, ParserConfigurationException, SAXException
	{
		String text = documentAsText("adams/v1/natural-order.xml");
		Document doc = document();

		DocumentWriterConfig config = new DocumentWriterConfig();

		DocumentWriter writer = new DocumentWriter(config);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		writer.write(doc, baos);

		String output = baos.toString();
		Assert.assertEquals(text, output);
	}

	private List<Book> books()
			throws IOException, ParserConfigurationException, SAXException
	{
		Document doc = document();

		List<Book> books = new ArrayList<>();

		NodeList bookNodes = doc.getElementsByTagName("book");
		for (int i = 0; i < bookNodes.getLength(); i++) {
			Element eBook = (Element) bookNodes.item(i);

			NamedNodeMap attributes = eBook.getAttributes();
			String year = attributes.getNamedItem("year").getNodeValue();
			String title = attributes.getNamedItem("title").getNodeValue();
			String wikidata = attributes.getNamedItem("wikidata")
					.getNodeValue();
			books.add(new Book(year, title, wikidata));
		}

		return books;
	}

	private Document document()
			throws IOException, SAXException, ParserConfigurationException
	{
		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResource("adams/v1/source.xml").openStream();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(input);
	}

	private String documentAsText(String resource)
			throws IOException, SAXException, ParserConfigurationException
	{
		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResource(resource).openStream();
		return IOUtils.toString(input);
	}

}
