package name.ulbricht.streams.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.api.StreamSource;
import name.ulbricht.streams.api.TerminalOperation;

final class StreamOperationClassListCellRenderer implements ListCellRenderer<Class<? extends StreamOperation>> {

	private static Icon sourceIcon = Messages.getIcon("StreamSource.icon");
	private static Icon intermediateIcon = Messages.getIcon("IntermediateOperation.icon");
	private static Icon terminalIcon = Messages.getIcon("TerminalOperation.icon");

	private final ListCellRenderer<Object> delegate = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(final JList<? extends Class<? extends StreamOperation>> list,
			final Class<? extends StreamOperation> value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		final var text = value != null ? StreamOperation.getDisplayName(value) : " ";

		final var component = delegate.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);

		if (component instanceof JLabel) {
			final var label = (JLabel) component;
			Icon icon = null;
			if (value != null) {
				if (StreamSource.class.isAssignableFrom(value))
					icon = sourceIcon;
				else if (IntermediateOperation.class.isAssignableFrom(value))
					icon = intermediateIcon;
				else if (TerminalOperation.class.isAssignableFrom(value))
					icon = terminalIcon;
			}
			label.setIcon(icon);
		}

		return component;
	}
}
