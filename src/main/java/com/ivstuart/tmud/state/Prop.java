package com.ivstuart.tmud.state;

public class Prop extends BasicThing {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8257487772034485327L;

	protected String alias;

	protected String affects;

	protected boolean waterSource;

	public Prop() {
	}

	public Prop(Prop prop_) {
		super(prop_);
		alias = prop_.alias;
		affects = prop_.affects;
		waterSource = false;
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
