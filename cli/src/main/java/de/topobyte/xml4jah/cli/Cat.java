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

package de.topobyte.xml4jah.cli;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.topobyte.xml4jah.core.DocumentWriterConfig;
import de.topobyte.xml4jah.dom.DocumentWriter;

public class Cat
{

	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException
	{
		if (args.length != 1) {
			System.out.println("Please specify exactly one file");
			System.exit(1);
		}

		String argInput = args[0];

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		FileInputStream input = new FileInputStream(argInput);

		Document document = builder.parse(input);

		DocumentWriterConfig config = new DocumentWriterConfig();

		DocumentWriter writer = new DocumentWriter(config);
		writer.write(document, System.out);
	}

}
