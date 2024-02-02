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

package com.ivstuart.tmud.state.mobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public class MobCommon implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final long serialVersionUID = 1L;
    private int level;
    private String name;

    private boolean running = false;

    public MobCommon() {

    }

    public MobCommon(MobCommon mobCommon) {
        this.level = mobCommon.level;
        this.name = mobCommon.name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public String toString() {
        return "MobCommon{" +
                "level=" + level +
                ", name='" + name + '\'' +
                ", running=" + running +
                '}';
    }
}
