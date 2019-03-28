package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public final class EnumEditor extends JComboBox<String> implements PropertyValueEditor {

	private final Class<? extends Enum<?>> enumClass;

	public EnumEditor(Class<? extends Enum<?>> enumClass) {
		this.enumClass = enumClass;
		try {
			Enum<?>[] values = (Enum<?>[]) enumClass.getMethod("values").invoke(null);
			for (Enum<?> value : values) {
				addItem(value.name());
			}
		} catch (ReflectiveOperationException ex) {
			throw new RuntimeException("Could not get enum values from " + this.enumClass, ex);
		}
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Object getValue() {
		final var s = (String) getSelectedItem();
		try {
			return enumClass.getMethod("valueOf", String.class).invoke(null, s);
		} catch (ReflectiveOperationException ex) {
			throw new RuntimeException(String.format("Could not get enum value '%s' from %s", s, this.enumClass), ex);
		}
	}

	@Override
	public void setValue(final Object value) {
		setSelectedItem(value != null ? ((Enum<?>) value).name() : null);
	}
}
