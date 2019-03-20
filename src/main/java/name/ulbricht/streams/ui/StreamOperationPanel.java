package name.ulbricht.streams.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import name.ulbricht.streams.api.StreamOperations;

final class StreamOperationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JLabel nameLabel;
	private final JLabel configLabel;

	StreamOperationPanel() {
		setLayout(new GridLayout(0, 1, 2, 2));

		this.nameLabel = new JLabel();
		this.nameLabel.setFont(this.nameLabel.getFont().deriveFont(Font.BOLD));

		this.configLabel = new JLabel();

		add(this.nameLabel);
		add(this.configLabel);
	}

	@Override
	public void setBorder(final Border border) {
		final var emptyBorder = new EmptyBorder(2, 2, 2, 2);

		if (border != null) {
			super.setBorder(new CompoundBorder(border, emptyBorder));
		} else {
			super.setBorder(emptyBorder);
		}
	}

	@Override
	public void setForeground(final Color fg) {
		super.setForeground(fg);
		if (this.nameLabel != null)
			this.nameLabel.setForeground(fg);
		if (this.configLabel != null)
			this.configLabel.setForeground(fg);
	}

	void updateContent(final Object streamOperation) {
		this.nameLabel.setText(streamOperation != null ? streamOperation.getClass().getSimpleName() : " ");

		Icon icon = null;
		String description = null;

		if (streamOperation != null) {
			final var streamOperationClass = streamOperation.getClass();

			String iconName = null;
			if (StreamOperations.isSourceOperation(streamOperationClass))
				iconName = Icons.SOURCE_OPERATION;
			else if (StreamOperations.isIntermediateOperation(streamOperationClass))
				iconName = Icons.INTERMEDIATE_OPERATION;
			else if (StreamOperations.isTerminalOperation(streamOperationClass))
				iconName = Icons.TERMINAL_OPERATION;

			icon = iconName != null ? Icons.getIcon(iconName, Icons.Size.X_SMALL) : null;
			description = StreamOperations.getDescription(streamOperation.getClass());
		}

		this.nameLabel.setIcon(icon);
		this.setToolTipText(
				description != null ? String.format("<html><p width=\"300px\">%s</p</html>", description) : null);

		this.configLabel.setText(streamOperation != null ? omit(createConfigurationText(streamOperation), 100) : " ");

	}

	private static String createConfigurationText(final Object streamOperation) {
		final var properties = StreamOperations.getProperties(streamOperation.getClass());
		if (properties.length > 0) {
			return Stream.of(properties).map(
					p -> String.format("%s=%s", p.getName(), StreamOperations.getPropertyValue(p, streamOperation)))
					.collect(Collectors.joining(", ", "", ""));
		}
		return "";
	}

	private static String omit(final String s, final int maxLength) {
		if (s.length() > maxLength)
			return s.substring(0, maxLength) + "…";
		return s;
	}
}
