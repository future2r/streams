package name.ulbricht.streams.impl.source;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class CharactersConfigurationPane extends AbstractConfigurationPane<Characters> {

	private JTextField textArea;

	@Override
	protected void createContent(final Container contentPane) {
		this.textArea = new JTextField(30);
		final var textLabel = new JLabel("Text:");

		contentPane.add(textLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 0, 4), 0, 0));
		contentPane.add(new JScrollPane(this.textArea), new GridBagConstraints(0, 1, 1, 1, 1, 1,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
	}

	@Override
	protected void initContent(final Characters operation) {
		this.textArea.setText(operation.getText());
	}

	@Override
	protected boolean applyContent(final Characters operation) {
		operation.setText(this.textArea.getText());
		return true;
	}
}
