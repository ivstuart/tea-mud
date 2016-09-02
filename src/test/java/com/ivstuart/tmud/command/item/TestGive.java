package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;
import org.junit.Test;

public class TestGive {

	@Test
	public void test() {
		Mob mob = new Mob();
		Mob ste = new Mob();

		CommandProvider.getCommand(Give.class).execute(mob,"50 copper Ste");
	}

}
