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

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Ivan on 28/09/2016.
 */
public class StringUtilTest {


    @Test
    public void testFirstWord() {
        String result = StringUtil.getFirstWord("firstword secondword thirdword");

        assertEquals("They match", "firstword", result);
    }

    @Test
    public void testLastWord() {
        String result = StringUtil.getLastWord("firstword secondword thirdword");

        assertEquals("They match", "thirdword", result);
    }

    @Test
    public void testLastWords() {
        String result = StringUtil.getLastWord("firstword secondword thirdword", "firstword".length(), "default");

        assertEquals("They match", "thirdword", result);
    }

    @Test
    public void testLastWordsDefault() {
        String result = StringUtil.getLastWord("firstword secondword thirdword", "firstword secondword thirdword".length(), "default");

        assertEquals("They match", "default", result);
    }

    @Test
    public void testFirstFewWords() {
        String result = StringUtil.getFirstFewWords("firstword secondword thirdword");

        assertEquals("They match", "firstword secondword", result);
    }
}
