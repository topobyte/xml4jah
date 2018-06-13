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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class TestConvert2
{

	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException
	{
		List<Book> books = Util.books1(Util.document("adams/v1/source.xml"));

		Map<Integer, List<Book>> decades = new TreeMap<>();
		for (Book book : books) {
			int year = Integer.parseInt(book.getYear());
			int decade = decade(year);
			List<Book> list = decades.get(decade);
			if (list == null) {
				list = new ArrayList<>();
				decades.put(decade, list);
			}
			list.add(book);
		}

		System.out.println("<books>");
		for (int decade : decades.keySet()) {
			List<Book> list = decades.get(decade);
			System.out
					.println(String.format("  <decade years=\"%ss\">", decade));
			for (Book book : list) {
				System.out.println(String.format(
						"    <book year=\"%s\" wikidata=\"%s\" title=\"%s\"/>",
						book.getYear(), book.getWikidata(), book.getTitle()));
			}
			System.out.println("  </decade>");
		}
		System.out.println("</books>");
	}

	private static int decade(int year)
	{
		if (year >= 2000) {
			return 2000 + (year - 2000) / 10 * 10;
		}
		return (year - 1900) / 10 * 10;
	}

}
