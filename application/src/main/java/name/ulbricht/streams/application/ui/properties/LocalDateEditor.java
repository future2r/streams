package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public final class LocalDateEditor extends JTextField implements PropertyValueEditor {

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Object getValue() {
		String s = getText();
		if (s != null && !s.isBlank())
			return LocalDate.parse(s, DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
		return null;
	}

	@Override
	public void setValue(final Object value) {
		setText(value != null ? ((LocalDate) value).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)) : "");
	}
}
