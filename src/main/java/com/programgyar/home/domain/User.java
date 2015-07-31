package com.programgyar.home.domain;

import com.programgyar.memdb.PersistentData;

public class User extends PersistentData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3072908854782951550L;
	public String name;
	public int age;

	@Override
	public String toString() {
		return super.toString() + " - " + name + " - " + age;
	}

	@Override
	protected void afterLoad() {
		System.out.println("afterLoad: " + this);
	}
}