package com.ivstuart.tmud.command.item;

import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestGive {

	@Test
	public void test() {
		Mob mob = new Mob();
		Mob ste = new Mob();

		// TODO create a room in which this can happen

		CommandProvider.getCommand(Give.class).execute(mob,"50 copper Ste");
	}

}
