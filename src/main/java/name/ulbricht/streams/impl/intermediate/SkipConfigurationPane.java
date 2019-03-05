package name.ulbricht.streams.impl.intermediate;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class SkipConfigurationPane extends AbstractConfigurationPane<Skip> {

	private JSpinner skipSpinner;

	@Override
	protected void createContent(final Container contentPane) {
		this.skipSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		final var skipLabel = new JLabel("Skip Elements:");

		contentPane.add(skipLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.skipSpinner, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

	}

	@Override
	protected void initContent(final Skip operation) {
		this.skipSpinner.setValue(operation.getSkip());
	}

	@Override
	protected boolean applyContent(final Skip operation) {
		operation.setSkip(((Number) this.skipSpinner.getValue()).intValue());
		return true;
	}
}
