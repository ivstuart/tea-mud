package com.ivstuart.tmud.state;

import java.io.Serializable;

public class Attribute implements Serializable {

	@Override
	public String toString() {
		return "Attribute{" +
				"name='" + name + '\'' +
				", minimum=" + minimum +
				", maximum=" + maximum +
				", current=" + current +
				", savedMaximum=" + savedMaximum +
				'}';
	}

	private static final long serialVersionUID = 1L;

	protected final String name;

	protected int minimum;

	protected int maximum;

	protected int current;

	protected int savedMaximum;

	public Attribute(Attribute oldAttribute_) {
		name = oldAttribute_.name;
		current = oldAttribute_.current;
		maximum = oldAttribute_.maximum;
		minimum = oldAttribute_.minimum;
	}

	public Attribute(String name_, int value) {
		name = name_;
		maximum = value;
		current = value;
		minimum = 0;
	}

	public Attribute(String aType, int min, int value) {
		name = aType;
		maximum = value;
		current = value;
		minimum = min;
	}

	public Attribute(String aType, int min, int max, int value) {
		name = aType;
		maximum = max;
		current = value;
		minimum = min;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.statistics.AttributeInterface#decrease(int)
	 */
	public void decrease(int value) {
		current -= value;
		if (current < minimum) {
			current = minimum;
		}
	}

	public boolean deduct(int value) {
		if (value <= current) {
			current -= value;
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.statistics.AttributeInterface#toString() TODO this is not the
	 * place for such presentation logic
	 */
	public String getDescription() {
		String colour = "$J";
		if (current < maximum) {
			colour = "$G";
		} else if (current > maximum) {
			colour = "$K";
		}

		return String.format("   %1$14s:" + colour + "%2$4s$J/%3$4s", name,
				current, maximum);

		// return Padding.pad(name, 12) + ":" + colour + Padding.ppad(current,
		// 4) + "$J/" + Padding.ppad(maximum, 4) + "  ";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.statistics.AttributeInterface#getMaximum()
	 */
	public int getMaximum() {
		return maximum;
	}

	public String getName() {
		return name;
	}

	public int getPercentageLeft() {
		return (100 * current) / maximum;
	}

	public String getPrompt() {
		return current + "/" + maximum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.statistics.AttributeInterface#getValue()
	 */
	public int getValue() {
		return current;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.statistics.AttributeInterface#increase(int)
	 */
	public void increase(int value) {
		current += value;
		if (current > maximum) {
			current = maximum;
		}
	}

	public void increaseMaximum(int value) {
		maximum += value;
	}

	public void restore() {
		current = maximum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see person.statistics.AttributeInterface#setValue(int)
	 */
	public void setValue(int value) {
		current = value;
		this.decrease(0);
		this.increase(0);
	}

	public boolean isMaximum() {
    	return current >= maximum;
    }

    public void savePoint() {
		savedMaximum = maximum;
    }

	public void rollback() {
		maximum = savedMaximum;
		increase(0);
	}
}
