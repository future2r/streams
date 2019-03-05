package name.ulbricht.streams.impl.intermediate;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class IntegerCompareFilterConfigurationPane extends AbstractConfigurationPane<IntegerCompareFilter> {

	private JComboBox<IntegerCompareFilter.CompareOperation> compareComboBox;
	private JSpinner valueSpinner;

	@Override
	protected void createContent(final Container contentPane) {
		this.compareComboBox = new JComboBox<>(IntegerCompareFilter.CompareOperation.values());
		final var compareLabel = new JLabel("Compare Operation:");

		this.valueSpinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		final var valueLabel = new JLabel("Compare Value:");

		contentPane.add(compareLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.compareComboBox, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		contentPane.add(valueLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.valueSpinner, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

	}

	@Override
	protected void initContent(final IntegerCompareFilter operation) {
		this.compareComboBox.setSelectedItem(operation.getCompareOperation());
		this.valueSpinner.setValue(operation.getCompareValue());
	}

	@Override
	protected boolean applyContent(final IntegerCompareFilter operation) {
		operation.setCompareOperation((IntegerCompareFilter.CompareOperation) this.compareComboBox.getSelectedItem());
		operation.setCompareValue(((Number) this.valueSpinner.getValue()).intValue());
		return true;
	}
}
