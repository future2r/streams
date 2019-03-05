package name.ulbricht.streams.impl.intermediate;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class RegExSplitterConfigurationPane extends AbstractConfigurationPane<RegExSplitter> {

	private JTextField patternTextField;

	@Override
	protected void createContent(final Container contentPane) {
		this.patternTextField = new JTextField();
		final var patternLabel = new JLabel("Split Pattern:");

		contentPane.add(patternLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.patternTextField, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
	}

	@Override
	protected void initContent(final RegExSplitter operation) {
		this.patternTextField.setText(operation.getPattern());
	}

	@Override
	protected boolean applyContent(final RegExSplitter operation) {
		operation.setPattern(this.patternTextField.getText());
		return true;
	}
}
