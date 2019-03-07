package name.ulbricht.streams.ui;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.StreamExecutor;
import name.ulbricht.streams.api.StreamSource;
import name.ulbricht.streams.api.TerminalOperation;

public class StreamOperationTableCellRenderer implements TableCellRenderer {

	private static Icon sourceIcon = Messages.getIcon("StreamSource.icon");
	private static Icon intermediateIcon = Messages.getIcon("IntermediateOperation.icon");
	private static Icon terminalIcon = Messages.getIcon("TerminalOperation.icon");

	private final TableCellRenderer delegate = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {

		@SuppressWarnings("unchecked")
		final var model = (MutableTableModel<StreamExecutor.ExecutionLogger>) table.getModel();
		final var operation = model.getRow(row).getOperation();

		final var component = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (component instanceof JLabel) {
			final var label = (JLabel) component;
			Icon icon = null;
			if (operation != null) {
				if (operation instanceof StreamSource)
					icon = sourceIcon;
				else if (operation instanceof IntermediateOperation)
					icon = intermediateIcon;
				else if (operation instanceof TerminalOperation)
					icon = terminalIcon;
			}
			label.setIcon(icon);
		}

		return component;
	}
}
