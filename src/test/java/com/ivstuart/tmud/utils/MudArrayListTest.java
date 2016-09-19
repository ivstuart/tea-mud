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

package com.ivstuart.tmud.utils;


import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import junit.framework.TestCase;
import org.junit.Test;

public class MudArrayListTest extends TestCase {
	
	@Test
	public void testSingleValue() {
		MudArrayList<Mob> list = new MudArrayList<Mob>();
		
		Mob sheep = new Mob();
		String sheepName = "sheep";
		sheep.setName(sheepName);
        sheep.setAlias(sheepName);
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
        sword.setAlias("sword");
        list.add(sword);
		list.add(sword);
		
		Item longSword = new Item();

		longSword.setBrief("long sword");
		longSword.setLong("a sharp steel long sword");
		longSword.setId("sword");
        longSword.setAlias("sword");

        list.add(longSword);
		list.add(sword);
		
		assertEquals("Check found",longSword,list.get("3.sword"));
		assertNotSame("Check found",longSword,list.get("2.sword"));
		assertNotSame("Check found",longSword,list.get("4.sword"));
	}

}
