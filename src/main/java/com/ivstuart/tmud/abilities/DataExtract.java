/**
 * Copyright 2012 Ivan Stuart
 *
 * This file is part of Tea-Mud.
 *
 * Tea-Mud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tea-Mud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Tea-Mud. If not, see <http://www.gnu.org/licenses/>.
 */
package com.ivstuart.tmud.abilities;

public class DataExtract {

	// public static String DATE = "20100817";

	public static int DATE_INT = 20100817;

	public static void main(String args[]) {

		System.out.println(Integer.MAX_VALUE);

		methodOne();

		methodTwo();
	}

	private static void methodOne() {

		long now = System.nanoTime();

		String DATE = String.valueOf(DATE_INT);

		String year = DATE.substring(0, 4);

		String month = DATE.substring(4, 6);

		String day = DATE.substring(6, 8);

		long duration = System.nanoTime() - now;

		System.out.println("year [" + year + "] month [" + month + "] day ["
				+ day + "]");

		System.out.println("Time taken [" + duration + "]");
	}

	private static void methodTwo() {

		long now = System.nanoTime();

		int year = DATE_INT / 10000;

		int month = (DATE_INT / 100) % 100;

		int day = DATE_INT % 100;

		long duration = System.nanoTime() - now;

		System.out.println("year [" + year + "] month [" + month + "] day ["
				+ day + "]");

		System.out.println("Time taken [" + duration + "]");
	}
}
