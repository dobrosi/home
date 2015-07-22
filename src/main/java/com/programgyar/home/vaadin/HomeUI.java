package com.programgyar.home.vaadin;

import com.programgyar.home.domain.LogRecord;
import com.programgyar.home.service.TempService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

@Title("HOME")
@Theme("valo")
public class HomeUI extends UI {
	Grid contactList = new Grid();
	Button newContact = new Button("New contact" + TempService.getTemp());

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
		HorizontalLayout mainLayout = new HorizontalLayout(contactList, newContact);
		mainLayout.setSizeFull();

		// Split and allow resizing
		setContent(mainLayout);
	}
}