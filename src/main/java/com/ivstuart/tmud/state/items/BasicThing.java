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

package com.ivstuart.tmud.state.items;

import com.ivstuart.tmud.common.Gender;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.common.Msgable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class BasicThing implements Serializable, Cloneable, Msgable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger();
    private String id;
    private String brief;
    private String verbose;
    private String look;
    private String properties;
    private boolean hidden;
    private boolean invisible;
    private boolean detectInvisible;
    private boolean detectHidden;

    public BasicThing() {
        look = "";
    }

    public BasicThing(BasicThing thing_) {
        this.id = thing_.id;
        this.brief = thing_.brief;
        this.verbose = thing_.verbose;
        this.look = thing_.look;
        this.properties = thing_.properties;
        this.hidden = thing_.hidden;
        this.invisible = thing_.invisible;
        this.detectInvisible = false;
        this.detectHidden = false;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties_) {
        properties = properties_;
    }

    @Override
    public String toString() {
        return "BasicThing{" +
                "id='" + id + '\'' +
                ", brief='" + brief + '\'' +
                ", verbose='" + verbose + '\'' +
                ", look='" + look + '\'' +
                ", properties='" + properties + '\'' +
                ", hidden=" + hidden +
                ", invisible=" + invisible +
                ", detectInvisible=" + detectInvisible +
                ", detectHidden=" + detectHidden +
                '}';
    }

    @Override
    public Object clone() {
        BasicThing thing = null;
        try {
            thing = (BasicThing) super.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Problem cloning object", e);
        }
        return thing;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    @Override
    public Gender getGender() {
        return Gender.NEUTRAL;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id_) {
        id = id_;
    }

    public String getLook() {
        return look;
    }

    public void setLook(String look_) {
        if (!look.isEmpty()) {
            look += "\n";
        }
        look += look_;
    }

    @Override
    public String getName() {
        return id;
    }

    @Override
    public List<String> getSenseFlags() {
        return Collections.emptyList();
    }

    public String getVerbose() {
        return verbose;
    }

    @Override
    public boolean hasDetectHidden() {
        return false;
    }

    @Override
    public boolean hasDetectInvisible() {
        return detectInvisible;
    }

    public boolean hasProperty(String property_) {
        if (properties == null) {
            return false;
        }
        return (properties.contains(property_));
    }

    @Override
    public boolean hasSeeInDark() {
        return false;
    }

    @Override
    public boolean isBlinded() {
        return false;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public boolean isInDark() {
        return false;
    }

    @Override
    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean flag) {
        invisible = flag;
    }

    @Override
    public boolean isSleeping() {
        return false;
    }

    @Override
    public void out(Msg message) {

    }

    @Override
    public boolean isGood() {
        return true;
    }

    @Override
    public String getRaceName() {
        return null;
    }

    public void setDetectInvisible(boolean flag) {
        detectInvisible = flag;
    }

    public void setClearLook(String look_) {
        look = "";
    }

    public void setVerbose(String verbose) {
        this.verbose = verbose;
    }

    public void setLookClear(String look_) {
        look = "";
    }

    public void setShort(String brief) {
        this.brief = brief;
    }

    public boolean isPlayer() {
        return false;
    }


}
