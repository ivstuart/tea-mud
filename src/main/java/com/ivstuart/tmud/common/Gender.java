package com.ivstuart.tmud.common;

public enum Gender {

	MALE("him", "his", "he"), FEMALE("her", "her", "she"), NEUTRAL("it", "it",
			"it");

	private String m;
	private String s;
	private String e;

	private Gender(String m, String s, String e) {
		this.m = m;
		this.s = s;
		this.e = e;
	}

	public String getE() {
		return e;
	}

	public String getM() {
		return m;
	}

	public String getS() {
		return s;
	}

	public boolean isFemale() {
		return this == Gender.FEMALE;
	}

	public Gender swap() {
		if (this == Gender.FEMALE) {
			return Gender.MALE;
		} else if (this == Gender.MALE) {
			return Gender.FEMALE;
		}
		return Gender.NEUTRAL;

	}
}
