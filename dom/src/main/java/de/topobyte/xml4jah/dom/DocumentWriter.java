package de.topobyte.xml4jah.dom;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.topobyte.melon.strings.Strings;
import de.topobyte.system.utils.SystemProperties;
import de.topobyte.xml4jah.core.AttributeOrder;
import de.topobyte.xml4jah.core.DocumentWriterConfig;

public class DocumentWriter
{

	private static String LS = System
			.getProperty(SystemProperties.LINE_SEPARATOR);

	private OutputStream output;

	private DocumentWriterConfig config;

	public DocumentWriter()
	{
		config = new DocumentWriterConfig();
	}

	public DocumentWriter(DocumentWriterConfig config)
	{
		this.config = config;
	}

	/*
	 * Writing methods
	 */

	public void write(Document document, Path output) throws IOException
	{
		OutputStream os = Files.newOutputStream(output);
		write(document, os);
		os.close();
	}

	public void write(Document document, OutputStream output) throws IOException
	{
		this.output = output;
		NodeList nodes = document.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			write(node, 0);
			if (i < nodes.getLength() - 1) {
				write(LS);
			}
		}
		if (config.isWithEndingNewline()) {
			write(LS);
		}
	}

	/*
	 * Internal methods
	 */

	private void write(Node node, int depth) throws IOException
	{
		NodeList nodes = node.getChildNodes();
		int numChildren = nodes.getLength();
		if (numChildren == 0) {
			writeNoChildren(node, depth);
		} else {
			writeWithChildren(node, nodes, depth);
		}
	}

	private void writeNoChildren(Node node, int depth)
			throws DOMException, IOException
	{
		writeOpening(node, depth, true);
	}

	private void writeWithChildren(Node node, NodeList nodes, int depth)
			throws IOException
	{
		writeOpening(node, depth, false);
		write(LS);

		for (int i = 0; i < nodes.getLength(); i++) {
			Node child = nodes.item(i);
			write(child, depth + 1);
			write(LS);
		}

		writeEnding(node, depth);
	}

	private void writeOpening(Node node, int depth, boolean close)
			throws IOException
	{
		StringBuilder buf = new StringBuilder();
		indent(buf, depth);
		buf.append("<");
		String nodeName = node.getNodeName();
		buf.append(nodeName);

		AttributeOrder attributeOrder = config.getAttributeOrder(nodeName);
		if (node.getAttributes().getLength() != 0) {
			if (attributeOrder == null) {
				writeAttributes(buf, node.getAttributes());
			} else {
				writeAttributesWithOrder(buf, attributeOrder,
						node.getAttributes());
			}
		}

		if (close) {
			buf.append("/>");
		} else {
			buf.append(">");
		}
		write(buf.toString());
	}

	private void writeEnding(Node node, int depth) throws IOException
	{
		StringBuilder buf = new StringBuilder();
		indent(buf, depth);
		buf.append("</");
		buf.append(node.getNodeName());
		buf.append(">");
		write(buf.toString());
	}

	private void writeAttributes(StringBuilder buf, NamedNodeMap attributes)
			throws IOException
	{
		for (int i = 0; i < attributes.getLength(); i++) {
			Node item = attributes.item(i);
			buf.append(" ");
			appendAttribute(buf, item);
		}
	}

	private void writeAttributesWithOrder(StringBuilder buf,
			AttributeOrder attributeOrder, NamedNodeMap attributes)
	{
		Set<String> done = new HashSet<>();
		for (String attribute : attributeOrder.getAttributes()) {
			Node item = attributes.getNamedItem(attribute);
			if (item == null) {
				continue;
			}
			buf.append(" ");
			appendAttribute(buf, item);
			done.add(item.getNodeName());
		}
		for (int i = 0; i < attributes.getLength(); i++) {
			Node item = attributes.item(i);
			if (done.contains(item.getNodeName())) {
				continue;
			}
			buf.append(" ");
			appendAttribute(buf, item);
		}
	}

	private void appendAttribute(StringBuilder buf, Node item)
	{
		buf.append(item.getNodeName().toString());
		buf.append("=");
		buf.append('"');
		buf.append(item.getNodeValue().toString());
		buf.append('"');
	}

	private void indent(StringBuilder buf, int depth)
	{
		Strings.repeat(buf, config.getIndent(), depth);
	}

	private void write(String name) throws IOException
	{
		output.write(name.getBytes());
	}

}
