package com.programgyar.home.service.gpio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.programgyar.home.domain.PinDto;

public class GpioService {
	public static Map<String, PinDto> pinMap = new HashMap<>();

	public static void addListenerAllPorts(GpioPinListener listener) {
		final GpioController gpio = GpioFactory.getInstance();
		gpio.getProvisionedPins().forEach(f -> f.addListener(listener));
	}

	public static void startGpio(GpioPinListener l) {
		final GpioController gpio = GpioFactory.getInstance();

		gpio.provisionDigitalInputPin(RaspiPin.GPIO_00).addListener(l);
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
	}

	public static void addPin(PinDto pinDto) {
		final GpioController gpio = GpioFactory.getInstance();
		GpioPin pin;
		if (pinDto.mode == PinMode.DIGITAL_INPUT) {
			pin = gpio.provisionDigitalInputPin(RaspiPin.getPinByName(pinDto.name));
		} else if (pinDto.mode == PinMode.DIGITAL_OUTPUT) {
			pin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByName(pinDto.name));
		}
		pinMap.put(pinDto.name, pin);
	}

	public static void init(List<PinDto> pinList) {
		pinList.forEach(pin -> addPin(pin));
	}

	private static void removePin(PinDto pin) {
		final GpioController gpio = GpioFactory.getInstance();

	}

}