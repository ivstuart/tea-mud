package com.ivstuart.tmud.utils;


import org.junit.Test;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

import junit.framework.TestCase;

public class MudArrayListTest extends TestCase {
	
	@Test
	public void testSingleValue() {
		MudArrayList<Mob> list = new MudArrayList<Mob>();
		
		Mob sheep = new Mob();
		String sheepName = "sheep";
		sheep.setName(sheepName);
		sheep.setId(sheepName);
		list.add(sheep);
		
		assertTrue("Check found",list.contains(sheep));
		
		assertNotNull("Check found",list.get(sheepName));
	}
	
	@Test
	public void testGetThirdValue() {
		MudArrayList<Item> list = new MudArrayList<Item>();
		
		Item sword = new Item();

		sword.setBrief("short sword");
		sword.setLong("a dull edged bronze short sword");
		sword.setId("sword");
		list.add(sword);
		list.add(sword);
		
		Item longSword = new Item();

		longSword.setBrief("long sword");
		longSword.setLong("a sharp steel long sword");
		longSword.setId("sword");
		
		list.add(longSword);
		list.add(sword);
		
		assertEquals("Check found",longSword,list.get("3.sword"));
		assertNotSame("Check found",longSword,list.get("2.sword"));
		assertNotSame("Check found",longSword,list.get("4.sword"));
	}

}
