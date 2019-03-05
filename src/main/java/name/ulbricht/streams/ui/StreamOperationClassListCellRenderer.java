package name.ulbricht.streams.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import name.ulbricht.streams.api.StreamOperation;

final class StreamOperationClassListCellRenderer implements ListCellRenderer<Class<? extends StreamOperation>> {

	private final ListCellRenderer<Object> delegate = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(JList<? extends Class<? extends StreamOperation>> list,
			Class<? extends StreamOperation> value, int index, boolean isSelected, boolean cellHasFocus) {
		final String text = value != null ? StreamOperation.getDisplayName(value) : " ";

		return delegate.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
	}
}
