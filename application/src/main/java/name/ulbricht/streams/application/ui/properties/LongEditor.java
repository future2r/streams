package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public final class LongEditor extends JSpinner implements PropertyValueEditor {

	public LongEditor() {
		super(new SpinnerNumberModel(0L, Long.MIN_VALUE, Long.MAX_VALUE, 1L));
		// note: this creates a Double spinner
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Object getValue() {
		return ((Number) super.getValue()).longValue();
	}

	@Override
	public void setValue(final Object value) {
		super.setValue(value != null ? ((Number) value).doubleValue() : 0d);
	}
}
