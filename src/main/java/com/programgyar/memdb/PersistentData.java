package com.programgyar.memdb;

import java.io.Serializable;

public class PersistentData implements Serializable {
	protected void afterLoad() {
	};

	protected void beforeSave() {
	};

	protected void afterSave() {
	};
}