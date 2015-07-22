package com.programgyar.home.service;

import java.io.File;
import java.io.IOException;

import com.google.gwt.thirdparty.guava.common.base.Charsets;
import com.google.gwt.thirdparty.guava.common.io.Files;

public class TempService {
	public static String getTemp() {
		File f = new File("/sys/bus/w1/devices/28-00000636f3bc/w1_slave");

		try {
			return Files.toString(f, Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
