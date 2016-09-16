/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.Gender;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.common.Msgable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class BasicThing implements Serializable, Cloneable, Msgable {

	public static final long serialVersionUID = 1L;
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
		id = thing_.id;
		brief = thing_.brief;
		verbose = thing_.verbose;
		look = thing_.look;
		properties = thing_.properties;
		detectInvisible = false;
		detectHidden = false;
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
		if (look.length() > 0) {
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
		return (properties.indexOf(property_) > -1);
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

	public void setDetectInvisible(boolean flag) { detectInvisible = flag;}

	public void setClearLook(String look_) {
		look = "";
	}

	public void setLong(String verbose) {
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
