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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import de.topobyte.xml4jah.core.AttributeOrder;
import de.topobyte.xml4jah.core.DocumentWriterConfig;

public class TestDocumentCreation
{

	@Test
	public void test1()
			throws IOException, ParserConfigurationException, SAXException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();

		Element booksNode = doc.createElement("books");
		doc.appendChild(booksNode);

		booksNode.appendChild(createBook(doc, "1979", "Q3107329",
				"The Hitchhiker's Guide to the Galaxy"));
		booksNode.appendChild(createBook(doc, "1980", "Q578895",
				"The Restaurant at the End of the Universe"));
		booksNode.appendChild(createBook(doc, "1982", "Q721",
				"Life, the Universe and Everything"));

		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setAttributeOrder("book",
				new AttributeOrder(Arrays.asList("year", "wikidata", "title")));
		config.setPreserveEmptyLines(true);

		Util.test("adams/v6/no-empty-lines.xml", doc, config);
	}

	@Test
	public void test2()
			throws IOException, ParserConfigurationException, SAXException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();

		Element booksNode = doc.createElement("books");
		doc.appendChild(booksNode);

		booksNode.appendChild(createBook(doc, "1979", "Q3107329",
				"The Hitchhiker's Guide to the Galaxy"));
		booksNode.appendChild(doc.createTextNode("\n\n"));
		booksNode.appendChild(createBook(doc, "1980", "Q578895",
				"The Restaurant at the End of the Universe"));
		booksNode.appendChild(createBook(doc, "1982", "Q721",
				"Life, the Universe and Everything"));

		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setAttributeOrder("book",
				new AttributeOrder(Arrays.asList("year", "wikidata", "title")));
		config.setPreserveEmptyLines(true);

		Util.test("adams/v6/few-empty-lines.xml", doc, config);
	}

	@Test
	public void test3()
			throws IOException, ParserConfigurationException, SAXException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();

		Element booksNode = doc.createElement("books");
		doc.appendChild(booksNode);

		booksNode.appendChild(createBook(doc, "1979", "Q3107329",
				"The Hitchhiker's Guide to the Galaxy"));
		booksNode.appendChild(doc.createTextNode("\n\n\n"));
		booksNode.appendChild(createBook(doc, "1980", "Q578895",
				"The Restaurant at the End of the Universe"));
		booksNode.appendChild(doc.createTextNode("\n\n\n\n"));
		booksNode.appendChild(createBook(doc, "1982", "Q721",
				"Life, the Universe and Everything"));

		DocumentWriterConfig config = new DocumentWriterConfig();
		config.setAttributeOrder("book",
				new AttributeOrder(Arrays.asList("year", "wikidata", "title")));
		config.setPreserveEmptyLines(true);

		Util.test("adams/v6/many-empty-lines.xml", doc, config);
	}

	private Element createBook(Document doc, String year, String wikidata,
			String title)
	{
		Element book = doc.createElement("book");
		book.setAttribute("year", year);
		book.setAttribute("wikidata", wikidata);
		book.setAttribute("title", title);
		return book;
	}

}
