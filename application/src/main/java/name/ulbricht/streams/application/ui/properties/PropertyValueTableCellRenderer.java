package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

final class PropertyValueTableCellRenderer implements TableCellRenderer {

	private final TableCellRenderer delegate = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
		String text;
		if (value instanceof String) {
			text = ((String) value).replaceAll("\n|\r", " ");
		} else if (value instanceof LocalDate) {
			text = ((LocalDate) value).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
		} else {
			text = Objects.toString(value);
		}

		return delegate.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
	}
}
