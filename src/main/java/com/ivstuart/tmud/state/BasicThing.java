package com.ivstuart.tmud.state;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ivstuart.tmud.common.Gender;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.common.Msgable;

public class BasicThing implements Serializable, Cloneable, Msgable {

	public static final long serialVersionUID = 1L;

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

	@Override
	public Object clone() {
		BasicThing thing = null;
		try {
			thing = (BasicThing) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return thing;
	}

	public String getBrief() {
		return brief;
	}

	@Override
	public Gender getGender() {
		return Gender.NEUTRAL;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getLook() {
		return look;
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

	@Override
	public boolean isInDark() {
		return false;
	}

	@Override
	public boolean isInvisible() {
		return invisible;
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
	public String getRace() {
		return null;
	}

	public void setInvisible(boolean flag) {
		invisible = flag;
	}

	public void setDetectInvisible(boolean flag) { detectInvisible = flag;}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public void setClearLook(String look_) {
		look = "";
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setId(String id_) {
		id = id_;
	}

	public void setLong(String verbose) {
		this.verbose = verbose;
	}

	public void setLook(String look_) {
		if (look.length() > 0) {
			look += "\n";
		}
		look += look_;
	}

	public void setProperties(String properties_) {
		properties = properties_;
	}

	public void setShort(String brief) {
		this.brief = brief;
	}

	@Override
	public final String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
