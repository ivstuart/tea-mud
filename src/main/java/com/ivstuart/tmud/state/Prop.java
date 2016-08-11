package com.ivstuart.tmud.state;

public class Prop extends BasicThing {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8257487772034485327L;

	protected String alias;

	protected String affects;

	public Prop() {
	}

	public String getAlias() {
		return alias;
	}

	public Prop(Prop prop_) {
		super(prop_);
		alias = prop_.alias;
		affects = prop_.affects;
	}

	public String getAffects() {
		return affects;
	}

	public boolean isTeacher() {
		return false;
	}

	public String look() {

		return this.getBrief();
	}

	public void setAffects(String affects_) {
		this.affects = affects_;
	}

	public void setAlias(String alias_) {
		alias = alias_;
	}

}
