package name.ulbricht.streams.impl.intermediate;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class LimitConfigurationPane extends AbstractConfigurationPane<Limit> {

	private JSpinner limitSpinner;

	@Override
	protected void createContent(final Container contentPane) {
		this.limitSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		final var limitLabel = new JLabel("Maximum Size:");

		contentPane.add(limitLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.limitSpinner, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

	}

	@Override
	protected void initContent(final Limit operation) {
		this.limitSpinner.setValue(operation.getLimit());
	}

	@Override
	protected boolean applyContent(final Limit operation) {
		operation.setLimit(((Number) this.limitSpinner.getValue()).intValue());
		return true;
	}
}
