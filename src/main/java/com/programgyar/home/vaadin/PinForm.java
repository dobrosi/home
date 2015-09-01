package com.programgyar.home.vaadin;

import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.programgyar.home.domain.PinDto;
import com.programgyar.home.service.gpio.GpioService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class PinForm extends FormLayout {

	Button save = new Button("Save", this::save);
	Button cancel = new Button("Cancel", this::cancel);
	TextField address = new TextField("Address");
	ComboBox mode = new ComboBox("Mode");
	ComboBox state = new ComboBox("State");

	PinDto pinDto;

	BeanFieldGroup<PinDto> formFieldBindings;

	public PinForm() {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		mode.addItems(PinMode.values());
		mode.setFilteringMode(FilteringMode.OFF);
		mode.setTextInputAllowed(false);

		state.addItems(PinState.values());
		state.setFilteringMode(FilteringMode.OFF);
		state.setTextInputAllowed(false);

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		setVisible(false);
	}

	private void buildLayout() {
		setSizeUndefined();
		setMargin(true);

		HorizontalLayout actions = new HorizontalLayout(save, cancel);
		actions.setSpacing(true);

		addComponents(actions, address, mode, state);
	}

	public void save(Button.ClickEvent event) {
		try {
			formFieldBindings.commit();

			GpioService.createPin(pinDto);

			String msg = String.format("Saved '%s'.", pinDto.name);
			Notification.show(msg, Type.TRAY_NOTIFICATION);
			getUI().refreshPins(null);
		} catch (FieldGroup.CommitException e) {
			// Validation exceptions could be shown here
		}
	}

	public void cancel(Button.ClickEvent event) {
		// Place to call business logic.
		Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
		getUI().pinList.select(null);
	}

	void edit(PinDto pinDto) {
		this.pinDto = pinDto;
		if (pinDto != null) {
			// Bind the properties of the contact POJO to fiels in this form
			formFieldBindings = BeanFieldGroup.bindFieldsBuffered(pinDto, this);
			address.focus();
		}
		setVisible(pinDto != null);
	}

	@Override
	public MainUI getUI() {
		return (MainUI) super.getUI();
	}

}
