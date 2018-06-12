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
