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

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.topobyte.xml4jah.core.AttributeOrder;
import de.topobyte.xml4jah.core.DocumentWriterConfig;

public class TestDocumentWriter
{

	@Test
	public void testParseBooks()
			throws IOException, ParserConfigurationException, SAXException
	{
		List<Book> books = Util.books1(document());
		Assert.assertEquals(11, books.size());
	}

	@Test
	public void testRewrite()
			throws IOException, ParserConfigurationException, SAXException
	{
		test("adams/v1/source.xml", Arrays.asList("year", "wikidata", "title"));
	}

	@Test
	public void testRewriteNoEndAtNewline()
			throws IOException, ParserConfigurationException, SAXException
	{
		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setWithEndingNewline(false);
		config.setAttributeOrder("book",
				new AttributeOrder(Arrays.asList("year", "wikidata", "title")));

		Util.test("adams/v1/no-ending-newline.xml", document(), config);
	}

	@Test
	public void testRewriteNoXmlDelcaration()
			throws IOException, ParserConfigurationException, SAXException
	{
		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setWithDeclaration(false);
		config.setAttributeOrder("book",
				new AttributeOrder(Arrays.asList("year", "wikidata", "title")));

		Util.test("adams/v1/no-xml-declaration.xml", document(), config);
	}

	@Test
	public void testNaturalOrder()
			throws IOException, ParserConfigurationException, SAXException
	{
		DocumentWriterConfig config = new DocumentWriterConfig();
		Util.test("adams/v1/natural-order.xml", document(), config);
	}

	@Test
	public void testWithDefaultStandalone()
			throws IOException, ParserConfigurationException, SAXException
	{
		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setDeclareDefaultStandalone(true);
		Util.test("adams/v1/with-default-standalone.xml", document(), config);
	}

	@Test
	public void testWithStandaloneYes()
			throws IOException, ParserConfigurationException, SAXException
	{
		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setDeclareDefaultStandalone(true);
		Document doc = document();
		doc.setXmlStandalone(true);
		Util.test("adams/v1/with-standalone-yes.xml", doc, config);
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
		return Util.document("adams/v1/source.xml");
	}

}
