package com.programgyar.home.vaadin;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.programgyar.home.domain.LogRecord;
import com.programgyar.home.service.TempService;
import com.programgyar.home.service.gpio.GpioListener;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Title("HOME")
@Theme("valo")
@Push(transport = Transport.WEBSOCKET)
public class MainUI extends UI {
	Grid contactList = new Grid();
	Button newContact = new Button("New contact " + TempService.getTemp() + " °C", this::startClicked);

	@Override
	protected void init(VaadinRequest request) {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		contactList.setContainerDataSource(new BeanItemContainer<>(LogRecord.class));
		contactList.setColumnOrder("id", "date", "message");
		contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
	}

	private void buildLayout() {
		VerticalLayout mainLayout = new VerticalLayout(contactList, newContact);
		mainLayout.setSizeFull();

		// Split and allow resizing
		setContent(mainLayout);
	}

	private void startClicked(Button.ClickEvent e) {
		System.out.println("start clicked  " + e);
		GpioListener.run(this::gpioChange);
	}
	
	private void gpioChange(GpioPinDigitalStateChangeEvent event) {
		System.out.println("callback " + event);
	}
}