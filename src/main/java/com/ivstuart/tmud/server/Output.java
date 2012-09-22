package com.ivstuart.tmud.server;

public class Output {

	private static final String LINE_STRING = "-----------------------------------------------------------------------------";

	private static final char ANSI_IDENTIFIER = '$';

	private static final char LINE_SEPERATOR = '~';

	private static final int CHAR_INDEX_OFFSET = 64;

	/*
	 * A - Cyan B - Yellow C - Orange D - Green E - Light Grey F - Gray G - Red
	 * H - Magenta I - Pink J - White K - Blue L - Dark Green M - Dark Blue N -
	 * Dark Magenta O - Dark Cyan P - Black
	 */
	private static String colour[] = { "", "\u001b[1;36m", "\u001b[1;33m",
			"\u001b[0;33m", "\u001b[1;32m", "\u001b[0;37m", "\u001b[1;30m",
			"\u001b[0;31m", "\u001b[1;35m", "\u001b[1;31m", "\u001b[1;37m",
			"\u001b[1;34m", "\u001b[0;32m", "\u001b[0;34m", "\u001b[0;35m",
			"\u001b[0;36m", "\u001b[0;30m" };

	private static String getEscapeString(char aChar) {
		int index = aChar - CHAR_INDEX_OFFSET;
		if (index < 0 || index >= colour.length) {
			return colour[0];
		}
		return colour[index];
	}

	public static String getString(String aString, boolean ansi) {
		if (aString == null) {
			return "null";
		}

		int index = aString.indexOf(ANSI_IDENTIFIER);

		while (index > -1) {
			char aChar = aString.charAt(index + 1);
			String escapeString;

			if (ansi) {
				escapeString = getEscapeString(aChar);
			} else {
				escapeString = colour[0];
			}

			StringBuilder sb = new StringBuilder(aString);

			sb.replace(index, index + 2, escapeString);
			aString = sb.toString();
			index = aString.indexOf(ANSI_IDENTIFIER);
		}

		index = aString.indexOf(LINE_SEPERATOR);

		while (index > -1) {
			StringBuilder sb = new StringBuilder(aString);
			sb.replace(index, index + 1, LINE_STRING);
			aString = sb.toString();
			index = aString.indexOf(LINE_SEPERATOR);
		}

		return aString;

	}

	public Output() {
	}
}
