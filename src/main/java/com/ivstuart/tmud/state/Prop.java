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

public class Prop extends BasicThing {

	/**
	 *
	 */
	private static final long serialVersionUID = 8257487772034485327L;
	protected String alias;
	protected String affects;
	protected boolean waterSource;
	protected boolean isSittable;
	protected boolean isSleepable;
	public Prop() {
	}

	public Prop(Prop prop_) {
		super(prop_);
		alias = prop_.alias;
		affects = prop_.affects;
		waterSource = false;
	}

	@Override
	public String toString() {
		return super.toString() + "Prop{" +
				"alias='" + alias + '\'' +
				", affects='" + affects + '\'' +
				", waterSource=" + waterSource +
				", isSittable=" + isSittable +
				", isSleepable=" + isSleepable +
				'}';
	}

	public boolean isSittable() {
		return isSittable;
	}

	public void setSittable(boolean sittable) {
		isSittable = sittable;
	}

	public boolean isSleepable() {
		return isSleepable;
	}

	public void setSleepable(boolean sleepable) {
		isSleepable = sleepable;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias_) {
		alias = alias_;
	}

	public String getAffects() {
		return affects;
	}

	public void setAffects(String affects_) {
		this.affects = affects_;
	}

	public boolean isTeacher() {
		return false;
	}

	public String look() {

		return this.getBrief();
	}

	public boolean isWaterSource() {
		return waterSource;
	}

	public void setWaterSource(boolean waterSource) {
		this.waterSource = waterSource;
	}
}
