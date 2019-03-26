package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public final class IntegerEditor extends JSpinner implements PropertyValueEditor {

	public IntegerEditor() {
		super(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Object getValue() {
		return ((Number) super.getValue()).intValue();
	}
	
	@Override
	public void setValue(final Object value) {
		super.setValue(value != null ? ((Number)value).intValue() : 0);
	}
}
