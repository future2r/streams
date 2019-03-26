package name.ulbricht.streams.application.ui.properties;

import java.beans.PropertyDescriptor;
import java.util.List;

import name.ulbricht.streams.application.ui.common.MutableTableModel;

public final class PropertiesTableModel extends MutableTableModel<PropertyDescriptor> {

	private Object bean;

	public PropertiesTableModel() {
		super(List.of(new Column<>("Property", PropertyDescriptor::getName, String.class), new Column<>("Value")));

		getColumn(1).setValueReader(this::getPropertyValue);
		getColumn(1).setValueWriter(this::setPropertyValue);
	}

	public void updateBean(final Object bean, final PropertyDescriptor[] properties) {
		this.bean = bean;
		replaceAll(List.of(properties));
	}

	@SuppressWarnings("unchecked")
	private <V> V getPropertyValue(final PropertyDescriptor property) {
		try {
			return (V) property.getReadMethod().invoke(this.bean, (Object[]) null);
		} catch (final ReflectiveOperationException ex) {
			return null;
		}
	}

	private <V> void setPropertyValue(final PropertyDescriptor property, final V value) {
		try {
			property.getWriteMethod().invoke(this.bean, value);
		} catch (final ReflectiveOperationException ex) {
			// ignore
		}
	}
}
