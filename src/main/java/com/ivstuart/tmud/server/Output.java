/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.server;

import com.ivstuart.tmud.common.Colour;

public class Output {

	private static final String LINE_STRING = "-----------------------------------------------------------------------------";

	private static final char ANSI_IDENTIFIER = '$';

	private static final char LINE_SEPERATOR = '~';

    public Output() {
    }

	public static String getString(String message, boolean ansi) {
		if (message == null) {
			return "null";
		}

		if (ansi) {
			message = replaceAnsi(message);
		} else {
			message = removeAnsi(message);
		}

		message = replaceLineSeperators(message);

		return message;

	}

	public static String removeAnsi(String message) {

		StringBuilder sb = new StringBuilder();

		for (int index = 0; index < message.length(); index++) {
			char achar = message.charAt(index);

			// Skip unicode characters if present
			if (achar == 27) {
				// System.out.println("1. character ["+achar+"] ["+(int)achar+"] "+index);
				for (; index < message.length() - 1;) {
					achar = message.charAt(++index);
					if (achar == 109) {
						break;
					}
					// System.out.println("2. character ["+achar+"] ["+(int)achar+"] "+index);
				}
				continue;
			}
			if (achar == '$') {
				// System.out.println("3. character ["+achar+"] ["+(int)achar+"] "+index);
				index++;
				continue; // Effectively skips the next character safetly.
			} else {
				// System.out.println("4. character ["+achar+"] ["+(int)achar+"] "+index);
				sb.append(achar);
			}
		}

		return sb.toString();

	}

	public static String replaceAnsi(String message) {
		int index = message.indexOf(ANSI_IDENTIFIER);

		while (index > -1) {
			char aChar = message.charAt(index + 1);

			String escapeString = Colour.getEscapeString(aChar);

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
}
