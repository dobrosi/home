package com.programgyar.home.domain;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.programgyar.memdb.PersistentData;

public class PinDto extends PersistentData {
	public String name;

	public int address;

	public PinMode mode;

	public boolean value;

	public PinDto(GpioPin f) {
		this.name = f.getName();
		this.address = f.getPin().getAddress();
		this.mode = f.getMode();
		this.value = f.isPullResistance(PinPullResistance.PULL_UP);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public PinMode getMode() {
		return mode;
	}

	public void setMode(PinMode mode) {
		this.mode = mode;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
}