package com.ivstuart.tmud.utils;

import java.util.StringTokenizer;

public class Parser {

	// TODO new approach using split
	public static StringPair parseA(String input) {
		String[] args = input.split(" ", 2);
		return new StringPair(args[0], args[1]);
	}

	// TODO new approach using split
	public static StringPair parseB(String input) {
		String[] args = input.split(" ");
		return new StringPair(args[0], args[args.length - 1]);
	}

	public static StringPair parseMessage(String input) {
		StringTokenizer st = new StringTokenizer(input);
		String source = st.nextToken();
		String target = null;
		if (st.hasMoreTokens()) {
			target = input.substring(source.length() + 1);

		}
		return new StringPair(source, target);
	}

	public static StringPair parseTarget(String input) {
		StringTokenizer st = new StringTokenizer(input);
		String source = st.nextToken();
		String target = null;
		while (st.hasMoreTokens()) {
			target = st.nextToken();
		}
		return new StringPair(source, target);
	}

	public Parser() {
	}
}