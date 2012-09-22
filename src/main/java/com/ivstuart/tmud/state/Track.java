package com.ivstuart.tmud.state;

public class Track {

	private String who;
	private String direction;
	private boolean blood = false;

	private int age;
	private int depth;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Track other = (Track) obj;
		if (who == null) {
			if (other.who != null) {
				return false;
			}
		} else if (!who.equals(other.who)) {
			return false;
		}
		return true;
	}

	public int getAge() {
		return age;
	}

	public int getDepth() {
		return depth;
	}

	public String getDirection() {
		return direction;
	}

	public String getWho() {
		return who;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((who == null) ? 0 : who.hashCode());
		return result;
	}

	public boolean isBlood() {
		return blood;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setBlood(boolean blood) {
		this.blood = blood;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setWho(String who) {
		this.who = who;
	}
}
