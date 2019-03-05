package name.ulbricht.streams.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import name.ulbricht.streams.api.StreamOperation;

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
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (this.nameLabel != null)
			this.nameLabel.setForeground(fg);
		if (this.configTextField != null)
			this.configTextField.setForeground(fg);
	}

	void updateContent(final StreamOperation streamOperation) {
		this.nameLabel.setText(streamOperation != null ? StreamOperation.getDisplayName(streamOperation) : " ");
		this.configTextField.setText(streamOperation != null ? streamOperation.getConfigurationText() : " ");
	}
}
