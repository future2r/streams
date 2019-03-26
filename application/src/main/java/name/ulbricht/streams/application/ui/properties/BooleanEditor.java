package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public final class BooleanEditor extends JCheckBox implements PropertyValueEditor {

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Object getValue() {
		return isSelected();
	}

	@Override
	public void setValue(final Object value) {
		setSelected(value != null ? (Boolean) value : false);
	}
}
