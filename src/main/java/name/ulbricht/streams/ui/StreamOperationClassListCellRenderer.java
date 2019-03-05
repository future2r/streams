package name.ulbricht.streams.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import name.ulbricht.streams.api.StreamOperation;

final class StreamOperationClassListCellRenderer implements ListCellRenderer<Class<? extends StreamOperation>> {

	private final ListCellRenderer<Object> delegate = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(final JList<? extends Class<? extends StreamOperation>> list,
			final Class<? extends StreamOperation> value, final int index, final boolean isSelected,
			final boolean cellHasFocus) {
		final var text = value != null ? StreamOperation.getDisplayName(value) : " ";

		return delegate.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
	}
}
