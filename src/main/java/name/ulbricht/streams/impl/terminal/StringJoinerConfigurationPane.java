package name.ulbricht.streams.impl.terminal;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class StringJoinerConfigurationPane extends AbstractConfigurationPane<StringJoiner> {

	private JTextField delimiterTextField;
	private JTextField prefixTextField;
	private JTextField suffixTextField;

	@Override
	protected void createContent(final Container contentPane) {
		this.delimiterTextField = new JTextField();
		final var delimiterLabel = new JLabel("Delimiter:");

		this.prefixTextField = new JTextField();
		final var prefixLabel = new JLabel("Prefix:");

		this.suffixTextField = new JTextField();
		final var suffixLabel = new JLabel("Suffix:");

		contentPane.add(delimiterLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.delimiterTextField, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(prefixLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.prefixTextField, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(suffixLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.suffixTextField, new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
	}

	@Override
	protected void initContent(final StringJoiner operation) {
		this.delimiterTextField.setText(operation.getDelimiter());
		this.prefixTextField.setText(operation.getPrefix());
		this.suffixTextField.setText(operation.getSuffix());
	}

	@Override
	protected boolean applyContent(final StringJoiner operation) {
		operation.setDelimiter(this.delimiterTextField.getText());
		operation.setPrefix(this.prefixTextField.getText());
		operation.setSuffix(this.suffixTextField.getText());
		return true;
	}
}
