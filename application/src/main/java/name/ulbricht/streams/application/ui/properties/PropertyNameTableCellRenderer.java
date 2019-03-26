package name.ulbricht.streams.application.ui.properties;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

final class PropertyNameTableCellRenderer implements TableCellRenderer {

	private final TableCellRenderer delegate = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {

		final var model = (PropertiesTableModel) table.getModel();
		final var propertyDescriptor = model.getRow(row);

		final var component = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (component instanceof JLabel) {
			final var label = (JLabel) component;

			final var description = propertyDescriptor.getShortDescription();
			label.setToolTipText(
					description != null ? String.format("<html><p width=\"300px\">%s</p</html>", description) : null);
		}

		return component;
	}
}
