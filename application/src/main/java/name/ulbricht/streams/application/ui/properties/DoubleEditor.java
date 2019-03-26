package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public final class DoubleEditor extends JSpinner implements PropertyValueEditor {

	public DoubleEditor() {
		super(new SpinnerNumberModel(0d, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1d));
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Object getValue() {
		return ((Number) super.getValue()).doubleValue();
	}

	@Override
	public void setValue(final Object value) {
		super.setValue(value != null ? ((Number)value).doubleValue() : 0d);
	}
}
