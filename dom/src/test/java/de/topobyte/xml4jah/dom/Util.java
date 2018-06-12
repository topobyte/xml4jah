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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.topobyte.xml4jah.core.DocumentWriterConfig;

public class Util
{

	public static Document document(String resource)
			throws IOException, SAXException, ParserConfigurationException
	{
		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResource(resource).openStream();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(input);
	}

	public static String documentAsText(String resource) throws IOException
	{
		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResource(resource).openStream();
		return IOUtils.toString(input);
	}

	public static void test(String resource, Document doc,
			DocumentWriterConfig config)
			throws IOException, SAXException, ParserConfigurationException
	{
		String text = Util.documentAsText(resource);

		DocumentWriter writer = new DocumentWriter(config);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		writer.write(doc, baos);

		String output = baos.toString();
		Assert.assertEquals(text, output);
	}

}
