package com.ivstuart.tmud.utils;

//RegexDemo.java

import java.util.regex.*;

class RegexDemo {
	static String cvtLineTerminators(String s) {
		StringBuilder sb = new StringBuilder(80);

		int oldindex = 0, newindex;
		while ((newindex = s.indexOf("\\n", oldindex)) != -1) {
			sb.append(s.substring(oldindex, newindex));
			oldindex = newindex + 2;
			sb.append('\n');
		}
		sb.append(s.substring(oldindex));

		s = sb.toString();

		sb = new StringBuilder(80);

		oldindex = 0;
		while ((newindex = s.indexOf("\\r", oldindex)) != -1) {
			sb.append(s.substring(oldindex, newindex));
			oldindex = newindex + 2;
			sb.append('\r');
		}
		sb.append(s.substring(oldindex));

		return sb.toString();
	}

	// Convert \n and \r character sequences to their single character
	// equivalents

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("java RegexDemo regex text");
			return;
		}

		Pattern p;
		try {
			p = Pattern.compile(args[0]);
		} catch (PatternSyntaxException e) {
			System.err.println("Regex syntax error: " + e.getMessage());
			System.err.println("Error description: " + e.getDescription());
			System.err.println("Error index: " + e.getIndex());
			System.err.println("Erroneous pattern: " + e.getPattern());
			return;
		}

		String s = cvtLineTerminators(args[1]);
		Matcher m = p.matcher(s);

		System.out.println("Regex = " + args[0]);
		System.out.println("Text = " + s);
		System.out.println();
		while (m.find()) {
			System.out.println("Found " + m.group());
			System.out.println("  starting at index " + m.start()
					+ " and ending at index " + m.end());

			System.out.println(m.group(1));
			System.out.println(m.groupCount());
			System.out.println();
		}
	}
}