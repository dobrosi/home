package com.programgyar.home.domain;

import java.util.ArrayList;
import java.util.List;

import com.programgyar.memdb.PersistentData;

public class Users extends PersistentData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6326183188603552013L;
	public List<User> users = new ArrayList<>();
}