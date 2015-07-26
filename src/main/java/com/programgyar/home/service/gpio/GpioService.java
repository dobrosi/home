package com.programgyar.home.service.gpio;

import java.util.List;
import java.util.stream.Collectors;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;

public class GpioService {
	private static List<PinDto> pinList = null;

	public static List<PinDto> getPinList() {
		final GpioController gpio = GpioFactory.getInstance();

		if (pinList == null) {
			for (int pin = 0; pin < 16; pin++) {
				int dir = GpioUtil.getDirection(pin);
				int value = Gpio.digitalRead(pin);
				PinPullResistance resistance = value == 0 ? PinPullResistance.PULL_DOWN : PinPullResistance.PULL_UP;
				PinState state = value == 0 ? PinState.LOW : PinState.HIGH;
				Pin gpioPin = RaspiPin.getPinByName("GPIO " + pin);
				if (dir == GpioUtil.DIRECTION_IN) {
					gpio.provisionDigitalInputPin(gpioPin, resistance);
				} else {
					gpio.provisionDigitalOutputPin(gpioPin, state);
				}
			}
			pinList = gpio.getProvisionedPins().stream().map(f -> new PinDto(f)).collect(Collectors.toList());
		}

		return pinList;
	}

	public static void addListenerAllPorts(GpioPinListener listener) {
		final GpioController gpio = GpioFactory.getInstance();
		gpio.getProvisionedPins().forEach(f -> f.addListener(listener));
	}

	public static void startGpio(Pin pin) {
		final GpioController gpio = GpioFactory.getInstance();

		gpio.provisionDigitalInputPin(pin, PinPullResistance.PULL_DOWN);
	}
}
