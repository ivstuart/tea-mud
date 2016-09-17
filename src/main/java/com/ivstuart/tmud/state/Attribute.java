/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ivstuart.tmud.state;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public class Attribute implements Serializable {

	private static final Logger LOGGER = LogManager.getLogger();
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

	/**
	 * @return
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
	 * @see person.statistics.AttributeInterface#setValue(int)
	 */
    public void setValue(int value) {
        current = value;
        this.decrease(0);
        this.increase(0);
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

	public void increaseCurrentAndMaximum(int value) {
		maximum += value;
		current += value;
	}

	public void restore() {
		current = maximum;
	}

	public boolean isMaximum() {
    	return current >= maximum;
    }


    public void increasePercentage(int i) {
    	int inc = 1+ (int) (i * maximum) / 100;
		increase(inc);
    }

	public void increaseToMaximum(int amount) {
		maximum += amount;
		current = maximum;
	}

	public void invert() {
		current = -current;
	}
}
