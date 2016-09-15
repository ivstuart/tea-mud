/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.common;

public enum Colour {
	
	
	/** 
	 * $H for example means the same as Magenta colour in text. Useful in text encoding resource files.
	 * In code just directly use the Colour enum.
	 * 
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
	 * Q - Purple
	 */

	BLANK("",0), 
	LIGHTCYAN("\033[1;36m",1), 
	YELLOW("\033[1;33m",2), 
	BROWN("\033[0;33m",3),
	LIGHTGREEN("\033[1;32m",4), 
	GREY("\033[0;37m",5), 
	DARKGREY("\033[1;30m",6), 
	RED("\033[0;31m",7), 
	LIGHTPURPLE("\033[1;35m",8),
	LIGHTRED("\033[1;31m",9), 
	WHITE("\033[1;37m",10),
	LIGHTBLUE("\033[1;34m",11), 
	GREEN("\033[0;32m",12),
	BLUE("\033[0;34m",13), 
	CYAN("\033[0;36m",14),
	BLACK("\033[0;30m",15),
	PURPLE("\033[0;35m",16), 

	NONE("\033[0;0m",17), 
	BOLD("\033[1m",18), 
	UNDERLINE("\033[4m",19), 
	BLINK("\033[5m",20), 
	ITALICS("\033[6m",21), 

	BGWHITE("\033[47m",22), 
	BGGREEN("\033[42m",23), 
	BGBLUE("\033[44m",24), 
	BGRED("\033[41m",25), 
	BGYELLOW("\033[43m",26), 
	BGCYAN("\033[46m",27), 
	BGPURPLE("\033[45m",28), 
	BGBLACK("\033[40m",29), 
	BGDEFAULT("\033[49m",30);


	private static final int CHAR_INDEX_OFFSET = 64;

	private static Colour colour[] = new Colour[32];
    private String ansiCode;
    private int escapeIndex;

    private Colour(String escapeString, int escapeIndex) {
        ansiCode = escapeString;
        this.escapeIndex = escapeIndex;
    }

	public static String getEscapeString(char aChar) {

        // Lazy initialise this look up array, which is better than using ordinal values as programmer can make change in future
		if (colour[0] == null) {
			synchronized(Colour.class) {
				for (Colour col : Colour.values()) {
					colour[col.getIndex()] = col;
				}
			}
		}

        int index = aChar - CHAR_INDEX_OFFSET;
		if (index < 0 || index >= colour.length) {
			return colour[0].toString();
		}
		return colour[index].toString();
	}
	
	@Override
	public String toString() {
		return ansiCode;
	}


	public int getIndex() {
		return escapeIndex;
	}


}
