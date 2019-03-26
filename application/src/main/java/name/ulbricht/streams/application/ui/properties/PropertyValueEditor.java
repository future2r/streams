package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;

public interface PropertyValueEditor {

	Component getComponent();

	Object getValue();

	void setValue(final Object value);
}
