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

package com.ivstuart.tmud.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;

public class Msg {

    private static final Logger LOGGER = LogManager.getLogger();

    private Msgable source = null;

    private Msgable target = null;

    private Msgable item = null;

    private String message = null;

    // "<S-You/NAME> injure<S-/s> <T-you/NAME> with <S-your/GEN-his> forceful <I-NAME> attack"


    // How to handle sleeping, room only, hidden, invisible, dark rooms and
    // blindness or infravision

    public Msg() {
    }

    public Msg(Msgable source, Msgable target, Msgable item, String msg) {
        this.source = source;
        this.target = target;
        this.item = item;
        message = msg;
    }

    public Msg(Msgable source, Msgable target, String msg) {
        this.source = source;
        this.target = target;
        message = msg;
    }

    public Msg(Msgable source, String msg) {
        this.source = source;
        message = msg;
    }

    public Msg(String msg) {
        message = msg;
    }

    private static String getGender(String text, boolean uppercase) {
        String gender = text;

        gender = gender.toLowerCase();

        switch (gender) {
            case "he":
                gender = "she";
                break;
            case "his":
            case "him":
                gender = "her";
                break;
            case "himself":
            case "hisself":
                gender = "herself";
                break;
        }

        if (uppercase) {
            gender = gender.replaceFirst(gender, gender.substring(1)
                    .toUpperCase());
        }
        return gender;
    }

    public boolean canHear(Msgable observer, Msgable target) {

        return !observer.isSleeping();
    }

    public boolean canSee(Msgable observer, Msgable target) {

        if (observer.isSleeping()) {
            return false;
        }

        if (observer.isBlinded()) {
            return false;
        }

        if (target.isInDark() && !observer.hasSeeInDark()) {
            return false;
        }

        if (target.isHidden() && !observer.hasDetectHidden()) {
            return false;
        }

        return !target.isInvisible() || observer.hasDetectInvisible();
    }

    public boolean isReplacementTag(StringBuilder output, int index) {

        if (output.charAt(index) == '<') {

            if (output.length() > index + 2) {
                return output.charAt(index + 2) == '-';
            } else {
                return false;
            }
        }
        return false;
    }

    public String maleToFemale(String text) throws ParseException {

        LOGGER.trace("Gender before [" + text + "]");

        if (text == null || text.length() < 2) {
            throw new ParseException("Gender string too short", 0);
        }

        char a = text.charAt(0);

        boolean uppercase = 'A' < a && a < 'Z';

        String gender = getGender(text, uppercase);

        LOGGER.trace("Gender after [" + gender + "]");

        return gender;
    }

    public String parse(Msgable requester) throws ParseException {

        if (message == null) {
            throw new ParseException("Null message", 0);
        }

        StringBuilder output = new StringBuilder(message);

        for (int index = 0; index < output.length(); index++) {

            if (isReplacementTag(output, index)) {

                // Remove start tag
                output.deleteCharAt(index);

                Msgable tagMsgable;

                String unseen = "someone";

                switch (output.charAt(index)) {
                    case 'S':
                        tagMsgable = source;
                        break;
                    case 'T':
                        tagMsgable = target;
                        break;
                    case 'I':
                        tagMsgable = item;
                        unseen = "something";
                        break;
                    case '<':
                    case '-':
                        continue;
                    default:
                        throw new ParseException(message + "index=" + index + " character is '" + output.charAt(index), 1);
                }

                output.deleteCharAt(index);
                output.deleteCharAt(index);

                int divIndex = output.indexOf("/", index);

                String replacement;

                int endIndex = output.indexOf(">", index);

                if (endIndex == -1) {
                    throw new ParseException("End tag missing", index);
                }

                if (divIndex == -1) {
                    replacement = output.substring(index, endIndex);
                } else if (requester == tagMsgable) {
                    replacement = output.substring(index, divIndex);
                } else {
                    replacement = output.substring(divIndex + 1, endIndex);
                }

                NAME_REPLACEMENT:

                if (replacement.contains("NAME")) {

                    if (tagMsgable == null) {
                        replacement = "";
                        output.deleteCharAt(index);
                        break NAME_REPLACEMENT;
                    }

                    String name;
                    if (this.canSee(requester, tagMsgable)) {

                        name = assignNameBasedOnAlignment(requester, tagMsgable);

                    } else {
                        name = unseen;
                    }

                    replacement = name;

                }

                // Gender him her replacement code
                if (replacement.contains("GEN-")) {

                    replacement = replacement.substring(4);

                    if (tagMsgable.getGender() != null
                            && tagMsgable.getGender().isFemale()) {

                        replacement = maleToFemale(replacement);
                    }

                }

                LOGGER.trace(requester.getName() + " replacing [" + output.substring(index, endIndex + 1) + "]  with [" + replacement + "]");

                output.replace(index, endIndex + 1, replacement);

            }
        }

        return output.toString();
    }

    private String assignNameBasedOnAlignment(Msgable requester, Msgable tagMsgable) {

        if (tagMsgable.getRaceName() != null) {
            if (tagMsgable.isPlayer()) {
                if (requester.isGood() != tagMsgable.isGood()) {
                    return "+* " + tagMsgable.getRaceName() + " *+";
                }
            }
        }

        return tagMsgable.getName();

    }

    public String toString(Msgable requester) {

        try {
            return this.parse(requester);
        } catch (ParseException e) {
            LOGGER.error("Problem parsing message", e);
            return message;
        }

    }

}
