/* This file is part of xml4jah.
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.topobyte.xml4jah.dom;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.LookupTranslator;
import org.apache.commons.text.translate.NumericEntityEscaper;
import org.apache.commons.text.translate.UnicodeUnpairedSurrogateRemover;

// Code in this class is adapted from Apache Commons Text.
// The sources are classes StringEscapeUtils and EntityArrays.
public class Escaping
{

	public static String escapeXml10(final String input)
	{
		return ESCAPE_XML10.translate(input);
	}

	public static final Map<CharSequence, CharSequence> BASIC_ESCAPE;
	static {
		final Map<CharSequence, CharSequence> initialMap = new HashMap<>();
		initialMap.put("\"", "&quot;"); // " - double-quote
		initialMap.put("&", "&amp;"); // & - ampersand
		initialMap.put("<", "&lt;"); // < - less-than
		initialMap.put(">", "&gt;"); // > - greater-than
		BASIC_ESCAPE = Collections.unmodifiableMap(initialMap);
	}

	public static final Map<CharSequence, CharSequence> APOS_ESCAPE;
	static {
		final Map<CharSequence, CharSequence> initialMap = new HashMap<>();
		initialMap.put("'", "&apos;"); // XML apostrophe
		APOS_ESCAPE = Collections.unmodifiableMap(initialMap);
	}

	// We added this one
	public static final Map<CharSequence, CharSequence> REALLY_BASIC_ESCAPE;
	static {
		final Map<CharSequence, CharSequence> initialMap = new HashMap<>();
		initialMap.put("&", "&amp;"); // & - ampersand
		initialMap.put("<", "&lt;"); // < - less-than
		REALLY_BASIC_ESCAPE = Collections.unmodifiableMap(initialMap);
	}

	public static final CharSequenceTranslator ESCAPE_XML10;
	static {
		final Map<CharSequence, CharSequence> escapeXml10Map = new HashMap<>();
		escapeXml10Map.put("\u0000", StringUtils.EMPTY);
		escapeXml10Map.put("\u0001", StringUtils.EMPTY);
		escapeXml10Map.put("\u0002", StringUtils.EMPTY);
		escapeXml10Map.put("\u0003", StringUtils.EMPTY);
		escapeXml10Map.put("\u0004", StringUtils.EMPTY);
		escapeXml10Map.put("\u0005", StringUtils.EMPTY);
		escapeXml10Map.put("\u0006", StringUtils.EMPTY);
		escapeXml10Map.put("\u0007", StringUtils.EMPTY);
		escapeXml10Map.put("\u0008", StringUtils.EMPTY);
		escapeXml10Map.put("\u000b", StringUtils.EMPTY);
		escapeXml10Map.put("\u000c", StringUtils.EMPTY);
		escapeXml10Map.put("\u000e", StringUtils.EMPTY);
		escapeXml10Map.put("\u000f", StringUtils.EMPTY);
		escapeXml10Map.put("\u0010", StringUtils.EMPTY);
		escapeXml10Map.put("\u0011", StringUtils.EMPTY);
		escapeXml10Map.put("\u0012", StringUtils.EMPTY);
		escapeXml10Map.put("\u0013", StringUtils.EMPTY);
		escapeXml10Map.put("\u0014", StringUtils.EMPTY);
		escapeXml10Map.put("\u0015", StringUtils.EMPTY);
		escapeXml10Map.put("\u0016", StringUtils.EMPTY);
		escapeXml10Map.put("\u0017", StringUtils.EMPTY);
		escapeXml10Map.put("\u0018", StringUtils.EMPTY);
		escapeXml10Map.put("\u0019", StringUtils.EMPTY);
		escapeXml10Map.put("\u001a", StringUtils.EMPTY);
		escapeXml10Map.put("\u001b", StringUtils.EMPTY);
		escapeXml10Map.put("\u001c", StringUtils.EMPTY);
		escapeXml10Map.put("\u001d", StringUtils.EMPTY);
		escapeXml10Map.put("\u001e", StringUtils.EMPTY);
		escapeXml10Map.put("\u001f", StringUtils.EMPTY);
		escapeXml10Map.put("\ufffe", StringUtils.EMPTY);
		escapeXml10Map.put("\uffff", StringUtils.EMPTY);
		ESCAPE_XML10 = new AggregateTranslator(
				new LookupTranslator(REALLY_BASIC_ESCAPE),
				// new LookupTranslator(APOS_ESCAPE),
				new LookupTranslator(
						Collections.unmodifiableMap(escapeXml10Map)),
				NumericEntityEscaper.between(0x7f, 0x84),
				NumericEntityEscaper.between(0x86, 0x9f),
				new UnicodeUnpairedSurrogateRemover());
	}

}
