/*
 * Created on 04-Oct-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.constants;

import com.ivstuart.tmud.common.Colour;

import static com.ivstuart.tmud.common.Colour.*;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public enum ManaType {
	FIRE(RED, 'F'), EARTH(BROWN, 'E'), WATER(BLUE, 'W'), AIR(YELLOW, 'A'), COMMON(
			WHITE, '*');

	public static ManaType getManaType(String manaType) {
		try {
			return ManaType.valueOf(manaType);
		} catch (IllegalArgumentException e) {

		}
		return null;

	}

	private Colour _colour;

	private char _symbol;

	ManaType(Colour colour_, char symbol_) {
		_colour = colour_;
		_symbol = symbol_;
	}

	public Colour getColour() {
		return _colour;
	}

	public String getManaString() {
		return _colour.toString() + '(' + _symbol + ')' + WHITE;
	}
}
