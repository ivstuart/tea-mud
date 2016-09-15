/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

public enum MobCombatState {

	BASHED("[*]"), BASH_ALERT("[!]"), BASH_LAGGED("[|]"), SILENCED("="), CASTING(
			"(Casting)"), CIRCLING("o"), OFFBALANCE("/"), GROUNDFIGHTING("g"), IMMOBILE(
			"i"), BLINDED("-"), STUNNED("_"), CONFUSED("?"), HIDDEN("-.-"),INVISIBLE("-i-"),SNEAKING(".s."), FROZEN("#Frozen#");

	private String prompt;

	MobCombatState(String prompt) {
		this.prompt = prompt;
	}

	public String getPrompt() {
		return prompt;
	}

}
