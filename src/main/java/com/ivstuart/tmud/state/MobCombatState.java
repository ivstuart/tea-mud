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

package com.ivstuart.tmud.state;

public enum MobCombatState {

    BASHED("[*]"), BASH_ALERT("[!]"), BASH_LAGGED("[|]"), SILENCED("="), CASTING(
            "(Casting)"), CIRCLING("o"), OFFBALANCE("/"), GROUNDFIGHTING("g"), IMMOBILE(
            "i"), BLINDED("-"), STUNNED("_"), CONFUSED("?"), HIDDEN("-.-"), INVISIBLE("-i-"), SNEAKING(".s."), FROZEN("#Frozen#");

    private final String prompt;

    MobCombatState(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

}
