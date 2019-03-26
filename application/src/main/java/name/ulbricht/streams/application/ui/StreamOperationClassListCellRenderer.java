package name.ulbricht.streams.application.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import name.ulbricht.streams.application.ui.helper.StreamOperations;

final class StreamOperationClassListCellRenderer implements ListCellRenderer<Class<?>> {

	private final ListCellRenderer<Object> delegate = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(final JList<? extends Class<?>> list, final Class<?> value,
			final int index, final boolean isSelected, final boolean cellHasFocus) {

		final var text = value != null ? value.getSimpleName() : " ";

		final var component = delegate.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);

		if (component instanceof JLabel) {
			final var label = (JLabel) component;
			Icon icon = null;
			String description = null;

			if (value != null) {
				String iconName = null;
				if (StreamOperations.isSourceOperation(value))
					iconName = Icons.SOURCE_OPERATION;
				else if (StreamOperations.isIntermediateOperation(value))
					iconName = Icons.INTERMEDIATE_OPERATION;
				else if (StreamOperations.isTerminalOperation(value))
					iconName = Icons.TERMINAL_OPERATION;

				
				
				icon = iconName != null ? Icons.getIcon(iconName, Icons.Size.X_SMALL).orElse(null) : null;
				description = StreamOperations.getDescription(value);
			}

			label.setIcon(icon);
			label.setToolTipText(
					description != null ? String.format("<html><p width=\"300px\">%s</p</html>", description) : null);
		}

		return component;
	}
}
