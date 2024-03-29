/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.common;

import com.ivstuart.tmud.state.items.BasicThing;
import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;
import junit.framework.TestCase;
import org.junit.Test;

import java.text.ParseException;

public class TestMsg extends TestCase {

    private Mob source = null;
    private Mob target = null;
    private BasicThing item = null;

    @Override
    public void setUp() {

        source = new Mob();

        source.setName("Ivan");
        source.getMobBodyStats().setGender(Gender.FEMALE);

        target = new Mob();

        target.setName("Rabbit");
        target.getMobBodyStats().setGender(Gender.MALE);

        item = new Item();

        item.setId("item");

    }

    @Test
    public void testBadMessageEndTagMissing() {

        String msg = "<S-your/GEN-his forceful attack";

        Msg message = new Msg(source, target, msg);

        try {
            message.parse(target);

            fail();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBadMessageUnknownTag() {

        String msg = "<Z-your/GEN-his> forceful attack";

        Msg message = new Msg(source, target, msg);

        try {
            message.parse(target);

            fail();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBadMissingGender() {

        String msg = "<S-your/GEN-> forceful attack";

        Msg message = new Msg(source, target, msg);

        try {
            message.parse(target);

            fail();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testHimToHer() throws ParseException {

        Msg message = new Msg();

        assertEquals("Him to her", "her", message.maleToFemale("him"));

        assertEquals("Him to her", "she", message.maleToFemale("he"));

        assertEquals("Him to her", "herself", message.maleToFemale("himself"));

        assertEquals("Him to her", "herself", message.maleToFemale("hisself"));

        assertEquals("Him to her", "her", message.maleToFemale("his"));

    }

    @Test
    public void testNameGenderItemDefinedOther() throws ParseException {

        String msg = "<S-You/NAME> injure<S-/s> <T-you/NAME> with <S-your/GEN-his> forceful <I-NAME> attack";

        item.setId("sword");

        Msg message = new Msg(source, target, item, msg);

        assertEquals("Name replacement failed",
                "Ivan injures Rabbit with her forceful sword attack",
                message.toString(new Mob()));

    }

    @Test
    public void testNameGenderItemOther() throws ParseException {

        String msg = "<S-You/NAME> injure<S-/s> <T-you/NAME> with <S-your/GEN-his> forceful <I-NAME> attack";

        Msg message = new Msg(source, target, null, msg);

        assertEquals("Name replacement failed",
                "Ivan injures Rabbit with her forceful attack",
                message.toString(new Mob()));

    }

    @Test
    public void testNameGenderItemSource() throws ParseException {

        String msg = "<S-You/NAME> injure<S-/s> <T-you/NAME> with <S-your/GEN-his> forceful <I-NAME> attack";

        Msg message = new Msg(source, target, null, msg);

        assertEquals("Name replacement failed",
                "You injure Rabbit with your forceful attack",
                message.toString(source));

    }

    @Test
    public void testNameGenderItemTarget() throws ParseException {

        String msg = "<S-You/NAME> injure<S-/s> <T-you/NAME> with <S-your/GEN-his> forceful <I-NAME> attack";

        Msg message = new Msg(source, target, null, msg);

        assertEquals("Name replacement failed",
                "Ivan injures you with her forceful attack",
                message.toString(target));

    }

    @Test
    public void testNoneReplacementTag() {
        StringBuilder sb = new StringBuilder("<Suger>");

        Msg message = new Msg();

        assertFalse("Check is not replacement tag",
                message.isReplacementTag(sb, 0));
    }

    @Test
    public void testNoReplacements() throws ParseException {

        String msg = "Z-your/GEN-his> forceful attack";

        Msg message = new Msg(source, target, msg);

        assertEquals("No replacements", msg, message.toString(source));

    }

    @Test
    public void testParseException() {
        Msg message = new Msg();

        try {
            message.parse(null);

            fail();
        } catch (ParseException e) {

        }
    }

    @Test
    public void testReplacementTag() {
        StringBuilder sb = new StringBuilder("<S-");

        Msg message = new Msg();

        assertTrue("Check is replacement tag", message.isReplacementTag(sb, 0));
    }

}
