package name.ulbricht.streams.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.SourceOperation;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.api.TerminalOperation;

final class StreamOperationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JLabel nameLabel;
	private final JTextField configTextField;

	StreamOperationPanel() {
		setLayout(new GridLayout(0, 1, 2, 2));

		this.nameLabel = new JLabel();
		this.nameLabel.setFont(this.nameLabel.getFont().deriveFont(Font.BOLD));

		this.configTextField = new JTextField();
		this.configTextField.setBorder(null);
		this.configTextField.setOpaque(false);
		this.configTextField.setFont(this.configTextField.getFont().deriveFont(Font.PLAIN));
		this.configTextField.setEditable(false);

		add(this.nameLabel);
		add(this.configTextField);
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
		if (this.configTextField != null)
			this.configTextField.setForeground(fg);
	}

	void updateContent(final StreamOperation streamOperation) {
		this.nameLabel.setText(streamOperation != null ? StreamOperation.getDisplayName(streamOperation) : " ");

		final Icon icon;
		if (streamOperation instanceof SourceOperation)
			icon = Images.getSmallIcon(Images.SOURCE_OPERATION);
		else if (streamOperation instanceof IntermediateOperation)
			icon = Images.getSmallIcon(Images.INTERMEDIATE_OPERATION);
		else if (streamOperation instanceof TerminalOperation)
			icon = Images.getSmallIcon(Images.TERMINAL_OPERATION);
		else
			icon = null;
		this.nameLabel.setIcon(icon);

		this.configTextField.setText(streamOperation != null ? streamOperation.getConfigurationText() : " ");
	}
}
