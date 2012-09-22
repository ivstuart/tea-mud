package com.ivstuart.tmud.state;

public enum MobState {

	BASHED("[*]"), BASH_ALERT("[!]"), BASH_LAGGED("[|]"), SILENCED("="), CASTING(
			"(Casting)"), CIRCLING("o"), OFFBALANCE("/"), GROUNDFIGHTING("g"), IMMOBILE(
			"i"), BLINDED("-"), STUNNED("_"), CONFUSED("?");

	private String prompt;

	MobState(String prompt) {
		this.prompt = prompt;
	}

	public String getPrompt() {
		return prompt;
	}

}
