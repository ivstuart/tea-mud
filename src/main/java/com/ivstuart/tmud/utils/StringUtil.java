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

/**
 * Created by Ivan on 13/08/2016.
 */
public class StringUtil {

    private static final String SPACE = " ";
    private static final char SPACE_CHAR = ' ';

    public static String getFirstWord(String input) {

        int index = input.indexOf(SPACE);

        if (index > -1) {
            return input.substring(0, index);
        }

        return input;

    }

    public static String getLastWord(String input) {
        int index = input.lastIndexOf(SPACE);

        if (index > -1) {
            return input.substring(index + 1);
        }

        return input;
    }

    public static String getLastWord(String input, int beginIndex, String defaultString) {
        int index = input.lastIndexOf(SPACE);

        if (index > -1 && index >= beginIndex) {
            return input.substring(index + 1);
        }

        return defaultString;
    }

    public static String getFirstFewWords(String input) {
        int index = input.lastIndexOf(SPACE);

        if (index > -1) {
            return input.substring(0, index);
        }

        return input;
    }

    public static int getWordCount(String input) {
        int total = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == SPACE_CHAR) {
                total++;
            }
        }
        return total;
    }

    public static void main(String[] args) {

        System.out.println(getFirstWord("firstword secondword thirdword"));

        System.out.println(getLastWord("firstword secondword thirdword"));

        System.out.println(getFirstFewWords("firstword secondword thirdword"));


    }
}
