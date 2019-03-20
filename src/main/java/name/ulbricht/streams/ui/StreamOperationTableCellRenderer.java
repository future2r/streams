package name.ulbricht.streams.ui;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import name.ulbricht.streams.api.StreamExecutor;
import name.ulbricht.streams.api.StreamOperations;
import name.ulbricht.streams.ui.common.MutableTableModel;

public class StreamOperationTableCellRenderer implements TableCellRenderer {

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
				final var streamOperationClass = operation.getClass();

				String iconName = null;
				if (StreamOperations.isSourceOperation(streamOperationClass))
					iconName = Icons.SOURCE_OPERATION;
				else if (StreamOperations.isIntermediateOperation(streamOperationClass))
					iconName = Icons.INTERMEDIATE_OPERATION;
				else if (StreamOperations.isTerminalOperation(streamOperationClass))
					iconName = Icons.TERMINAL_OPERATION;
				
				icon = iconName != null ? Icons.getIcon(iconName, Icons.Size.X_SMALL) : null;
			}

			label.setIcon(icon);
		}

		return component;
	}
}
