package com.programgyar.home.domain;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.programgyar.memdb.PersistentData;

public class PinDto extends PersistentData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6249103365492718369L;

	public transient GpioPin pin;

	public String name;

	public String address;

	public PinMode mode;

	public PinState state;

	public PinDto(GpioPin pin) {
		this.pin = pin;

		this.name = pin.getName();
		this.address = "" + pin.getPin().getAddress();
		this.mode = pin.getMode();
		this.state = pin.isPullResistance(PinPullResistance.PULL_UP) ? PinState.HIGH : PinState.LOW;
	}

	public PinDto() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PinMode getMode() {
		return mode;
	}

	public void setMode(PinMode mode) {
		this.mode = mode;
	}

	public PinState getState() {
		return state;
	}

	public void setState(PinState state) {
		this.state = state;
	}

}
