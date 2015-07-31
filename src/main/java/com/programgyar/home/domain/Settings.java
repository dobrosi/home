package com.programgyar.home.domain;

import java.util.ArrayList;
import java.util.List;

import com.programgyar.home.service.HeatService;
import com.programgyar.home.service.gpio.GpioService;
import com.programgyar.memdb.PersistentData;

public class Settings extends PersistentData {
	public List<PinDto> pinList = new ArrayList<>();
	public HeatMode heatMode;

	@Override
	protected void afterLoad() {
		HeatService.switchMode(heatMode);
		GpioService.init(pinList);
	}
}