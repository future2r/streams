package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public final class CharsetEditor extends JComboBox<String> implements PropertyValueEditor {

	public CharsetEditor() {
		super(Charset.availableCharsets().keySet().toArray(String[]::new));
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Object getValue() {
		return Charset.forName((String) getSelectedItem());
	}

	@Override
	public void setValue(final Object value) {
		setSelectedItem(value != null ? ((Charset) value).name() : StandardCharsets.UTF_8.name());
	}
}
