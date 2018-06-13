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
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class TestConvert3
{

	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException
	{
		List<Book> books = Util.books1(Util.document("adams/v1/source.xml"));
		System.out.println("<books>");
		for (Book book : books) {
			System.out.println("  <book>");
			System.out.println(
					String.format("    <%s>%s</%1$s>", "year", book.getYear()));
			System.out.println(String.format("    <%s>%s</%1$s>", "title",
					book.getTitle()));
			System.out.println(String.format("    <%s>%s</%1$s>", "wikidata",
					book.getWikidata()));
			System.out.println("  </book>");
		}
		System.out.println("</books>");
	}

}
