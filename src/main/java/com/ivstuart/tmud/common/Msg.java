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

    public boolean canHear(Msgable observer, Msgable target) {

        if (observer.isSleeping()) {
            return false;
        }

        return true;
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

        if (target.isInvisible() && !observer.hasDetectInvisible()) {
            return false;
        }

        return true;
    }

    public boolean isReplacementTag(StringBuilder output, int index) {

        if (output.charAt(index) == '<') {

            if (output.length() > index + 2) {
                if (output.charAt(index + 2) != '-') {
                    return false;
                }
            } else {
                return false;
            }
            return true;
        }
        return false;
    }

    public String maleToFemale(String text) throws ParseException {

        LOGGER.trace("Gender before [" + text + "]");

        if (text == null || text.length() < 2) {
            throw new ParseException("Gender string too short", 0);
        }

        char a = text.charAt(0);

        boolean uppercase = false;

        if ('A' < a && a < 'Z') {
            uppercase = true;
        }

        String gender = new String(text);

        gender = gender.toLowerCase();

        if (gender.equals("he")) {
            gender = "she";
        } else if (gender.equals("his")) {
            gender = "her";
        } else if (gender.equals("him")) {
            gender = "her";
        } else if (gender.equals("himself")) {
            gender = "herself";
        } else if (gender.equals("hisself")) {
            gender = "herself";
        }

        if (uppercase) {
            gender = gender.replaceFirst(gender, gender.substring(1)
                    .toUpperCase());
        }

        LOGGER.trace("Gender after [" + gender + "]");

        return gender;
    }

    public String parse(Msgable requester) throws ParseException {

        if (message == null) {
            throw new ParseException(message, 0);
        }

        StringBuilder output = new StringBuilder(message);

        for (int index = 0; index < output.length(); index++) {

            if (isReplacementTag(output, index)) {

                // Remove start tag
                output.deleteCharAt(index);

                Msgable tagMsgable = null;

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
                        continue;
                    case '-':
                        continue;
                    default:
                        throw new ParseException(message + "index=" + index + " character is '" + output.charAt(index), 1);
                }

                output.deleteCharAt(index);
                output.deleteCharAt(index);

                int divIndex = output.indexOf("/", index);

                String replacement = null;

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

                if (replacement.indexOf("NAME") > -1) {

                    if (tagMsgable == null) {
                        replacement = "";
                        output.deleteCharAt(index);
                        break NAME_REPLACEMENT;
                    }

                    String name = null;
                    if (this.canSee(requester, tagMsgable)) {

                        name = assignNameBasedOnAlignment(tagMsgable);

                    } else {
                        name = unseen;
                    }

                    replacement = name;

                }

                // Gender him her replacement code
                if (replacement.indexOf("GEN-") > -1) {

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

    private String assignNameBasedOnAlignment(Msgable tagMsgable) {

        if (tagMsgable.getRaceName() != null && target != null) {
            if (source.isGood() != target.isGood()) {
                return "+* " + tagMsgable.getRaceName() + " *+";
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
