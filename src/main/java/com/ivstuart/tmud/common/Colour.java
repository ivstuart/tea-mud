package com.ivstuart.tmud.common;

public enum Colour {
	
	
	/*
	 * A - Cyan 
	 * B - Yellow
	 * C - Orange 
	 * D - Green
	 * E - Light Grey
	 * F - Gray 
	 * G - Red
	 * H - Magenta 
	 * I - Pink 
	 * J - White 
	 * K - Blue 
	 * L - Dark Green 
	 * M - Dark Blue 
	 * N - Dark Magenta 
	 * O - Dark Cyan 
	 * P - Black
	 */

	WHITE("\033[1;37m"), LIGHTGREEN("\033[1;32m"), LIGHTBLUE("\033[1;34m"), LIGHTRED(
			"\033[1;31m"), YELLOW("\033[1;33m"), LIGHTCYAN("\033[1;36m"), LIGHTPURPLE(
			"\033[1;35m"), GREY("\033[0;37m"), GREEN("\033[0;32m"), BLUE(
			"\033[0;34m"), RED("\033[0;31m"), BROWN("\033[0;33m"), CYAN(
			"\033[0;36m"), PURPLE("\033[0;35m"), DARKGREY("\033[1;30m"), BLACK(
			"\033[0;30m"),

	NONE("\033[0;0m"), BOLD("\033[1m"), UNDERLINE("\033[4m"), BLINK("\033[5m"), ITALICS(
			"\033[6m"),

	BGWHITE("\033[47m"), BGGREEN("\033[42m"), BGBLUE("\033[44m"), BGRED(
			"\033[41m"), BGYELLOW("\033[43m"), BGCYAN("\033[46m"), BGPURPLE(
			"\033[45m"), BGBLACK("\033[40m"), BGDEFAULT("\033[49m");

	private String ansiCode;
	private String encoding; // TODO 

	private Colour(String escapeString) {
		ansiCode = escapeString;
		encoding = "$H";
	}
	
	public String getAnsiCode() {
		return ansiCode;
	}

	@Override
	public String toString() {
		return ansiCode;
	}

}
