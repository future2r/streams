package name.ulbricht.streams.application.ui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

final class StreamOperationListCellRenderer implements ListCellRenderer<Object> {

	private final StreamOperationPanel panel = new StreamOperationPanel();

	@Override
	public Component getListCellRendererComponent(final JList<? extends Object> list, final Object value,
			final int index, final boolean isSelected, final boolean cellHasFocus) {
		this.panel.updateContent(value);

		this.panel.setBackground(isSelected ? UIManager.getColor("List.selectionBackground")
				: index % 2 == 0 ? UIManager.getColor("List.background") : UIManager.getColor("Panel.background"));
		this.panel.setForeground(
				isSelected ? UIManager.getColor("List.selectionForeground") : UIManager.getColor("List.foreground"));

		panel.setBorder(cellHasFocus ? UIManager.getBorder("List.focusCellHighlightBorder") : null);

		return this.panel;
	}
}
