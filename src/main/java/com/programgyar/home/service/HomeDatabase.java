package com.programgyar.home.service;

import com.programgyar.home.domain.Settings;
import com.programgyar.home.domain.User;
import com.programgyar.memdb.Database;

public class HomeDatabase extends Database {
	public static Settings getSettings() {
		return get(Settings.class, new Settings());
	}

	public static User getUser() {
		return get(User.class, new User());
	}
}
