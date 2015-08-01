package com.programgyar.memdb;

import java.io.Serializable;

public class PersistentData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2897929030026400913L;

	protected void afterLoad() {
	};

	protected void beforeSave() {
	};

	protected void afterSave() {
	};
}