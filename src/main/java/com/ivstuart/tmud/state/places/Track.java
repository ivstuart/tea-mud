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

package com.ivstuart.tmud.state.places;

public class Track {

    private String who;
    private String direction;
    private boolean blood = false;

    private int age;
    private int depth;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Track other = (Track) obj;
        if (who == null) {
            if (other.who != null) {
                return false;
            }
        } else if (!who.equals(other.who)) {
            return false;
        }
        return true;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((who == null) ? 0 : who.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Track{" +
                "who='" + who + '\'' +
                ", direction='" + direction + '\'' +
                ", blood=" + blood +
                ", age=" + age +
                ", depth=" + depth +
                '}';
    }

    public boolean isBlood() {
        return blood;
    }

    public void setBlood(boolean blood) {
        this.blood = blood;
    }
}
