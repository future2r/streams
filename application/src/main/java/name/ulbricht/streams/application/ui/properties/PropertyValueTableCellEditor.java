package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
final class PropertyValueTableCellEditor extends AbstractCellEditor implements TableCellEditor {

	private static final Map<Class<?>, Class<? extends PropertyValueEditor>> editors = new HashMap<>();

	static {
		editors.put(String.class, MultilineStringEditor.class);
		editors.put(Integer.class, IntegerEditor.class);
		editors.put(Integer.TYPE, IntegerEditor.class);
		editors.put(Long.class, LongEditor.class);
		editors.put(Long.TYPE, LongEditor.class);
		editors.put(Double.class, DoubleEditor.class);
		editors.put(Double.TYPE, DoubleEditor.class);
		editors.put(Boolean.class, BooleanEditor.class);
		editors.put(Boolean.TYPE, BooleanEditor.class);
		editors.put(Path.class, PathEditor.class);
		editors.put(LocalDate.class, LocalDateEditor.class);
		editors.put(Charset.class, CharsetEditor.class);
	}

	private PropertyValueEditor editor;

	@SuppressWarnings("unchecked")
	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected,
			final int row, final int column) {

		final var model = (PropertiesTableModel) table.getModel();
		final var property = model.getRow(row);
		final var propertyType = property.getPropertyType();

		if (propertyType.isEnum()) {
			this.editor = new EnumEditor((Class<? extends Enum<?>>) propertyType);

		} else {
			final Class<? extends PropertyValueEditor> editorClass = editors.get(propertyType);
			if (editorClass != null) {
				try {
					this.editor = editorClass.getConstructor().newInstance();
				} catch (final ReflectiveOperationException ex) {
					throw new RuntimeException("Could not created editor of " + editorClass, ex);
				}
			}
		}

		if (this.editor != null) {
			this.editor.setValue(value);
			return this.editor.getComponent();
		}

		throw new UnsupportedOperationException("No editor for " + propertyType);
	}

	@Override
	public Object getCellEditorValue() {
		if (this.editor != null)
			return this.editor.getValue();
		return null;
	}
}