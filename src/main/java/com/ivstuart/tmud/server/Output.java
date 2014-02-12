package com.ivstuart.tmud.server;

import java.text.Normalizer;

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

	public static String getString(String message, boolean ansi) {
		if (message == null) {
			return "null";
		}

		if (ansi) {
			message = replaceAnsi(message);
		}
		else {
			message = removeAnsi(message);
		}
		
		message = replaceLineSeperators(message);

		return message;

	}

	public static String removeAnsi(String message) {
		
		int index = message.indexOf(ANSI_IDENTIFIER);

		while (index > -1) {
			StringBuilder sb = new StringBuilder(message);
			sb.replace(index, index + 2, "");
			message = sb.toString();
			index = message.indexOf(ANSI_IDENTIFIER);
		}
		return message;
		
		
	}

	public static String replaceAnsi(String message) {
		int index = message.indexOf(ANSI_IDENTIFIER);

		while (index > -1) {
			char aChar = message.charAt(index + 1);
			
			String escapeString = getEscapeString(aChar);

			StringBuilder sb = new StringBuilder(message);

			sb.replace(index, index + 2, escapeString);
			message = sb.toString();
			index = message.indexOf(ANSI_IDENTIFIER);
		}
		return message;
	}

	public static String replaceLineSeperators(String aString) {
		int index;
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
