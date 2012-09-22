package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

public class Order implements Command {

	/*
	 * Usage: order <character> <command> order followers <command>
	 * 
	 * Used for ordering pets and charmed people to do your evil bidding. You
	 * can order everyone under your command with "order followers".
	 */
	@Override
	public void execute(Mob mob, String input) {

		mob.out("todo order and decide who accepts such orders");

	}

}