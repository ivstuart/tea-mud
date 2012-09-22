package com.ivstuart.tmud.constants;

public enum DoorState {
	OPEN("(", ")"), CLOSED("[", "]"), LOCKED("{", "}"), BOLTED("-", "-"), BROKEN(
			"~", "~");

	private String _begin;
	private String _end;

	DoorState(String begin_, String end_) {
		_begin = begin_;
		_end = end_;
	}

	public String getBegin() {
		return _begin;
	}

	public String getEnd() {
		return _end;
	}

	public boolean isScanable() {
		return (this == DoorState.OPEN || this == DoorState.BROKEN);
	}

}
