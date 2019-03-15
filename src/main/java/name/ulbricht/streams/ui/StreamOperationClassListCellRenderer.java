package name.ulbricht.streams.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.api.StreamOperations;

final class StreamOperationClassListCellRenderer implements ListCellRenderer<Class<?>> {

	private final ListCellRenderer<Object> delegate = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(final JList<? extends Class<?>> list, final Class<?> value,
			final int index, final boolean isSelected, final boolean cellHasFocus) {

		final var text = value != null ? StreamOperations.getDisplayName(value) : " ";

		final var component = delegate.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);

		if (value != null && component instanceof JLabel) {
			final var streamOperationAnnotation = value.getAnnotation(StreamOperation.class);

			final var label = (JLabel) component;
			Icon icon = null;
			if (value != null) {
				switch (streamOperationAnnotation.type()) {
				case SOURCE:
					icon = Icons.getSmallIcon(Icons.SOURCE_OPERATION);
					break;
				case INTERMEDIATE:
					icon = Icons.getSmallIcon(Icons.INTERMEDIATE_OPERATION);
					break;
				case TERMINAL:
					icon = Icons.getSmallIcon(Icons.TERMINAL_OPERATION);
					break;
				}
			}
			label.setIcon(icon);

			final var description = streamOperationAnnotation.description();
			if (!description.isEmpty())
				label.setToolTipText(String.format("<html><p width=\"300px\">%s</p</html>", description));
			else
				label.setToolTipText(null);
		}

		return component;
	}
}
