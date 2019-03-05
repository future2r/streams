package name.ulbricht.streams.impl.intermediate;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class StringFormatterConfigurationPane extends AbstractConfigurationPane<StringFormatter> {

	private JTextField formatTextField;

	@Override
	protected void createContent(final Container contentPane) {
		this.formatTextField = new JTextField();
		final var formatLabel = new JLabel("Format Pattern:");

		contentPane.add(formatLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.formatTextField, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
	}

	@Override
	protected void initContent(final StringFormatter operation) {
		this.formatTextField.setText(operation.getFormat());
	}

	@Override
	protected boolean applyContent(final StringFormatter operation) {
		operation.setFormat(this.formatTextField.getText());
		return true;
	}
}
