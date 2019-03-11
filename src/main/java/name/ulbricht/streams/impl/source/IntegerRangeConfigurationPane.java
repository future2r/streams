package name.ulbricht.streams.impl.source;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class IntegerRangeConfigurationPane extends AbstractConfigurationPane<IntegerRange> {

	private JSpinner startSpinner;
	private JSpinner endSpinner;
	private JCheckBox closedCheckBox;

	@Override
	protected void createContent(final Container contentPane) {
		this.startSpinner = new JSpinner(new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1));
		final var numberLabel = new JLabel("Start:");

		this.endSpinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		final var originLabel = new JLabel("End:");

		this.closedCheckBox = new JCheckBox("Range is closed");

		contentPane.add(numberLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.startSpinner, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		contentPane.add(originLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.endSpinner, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		contentPane.add(this.closedCheckBox, new GridBagConstraints(0, 2, 2, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
	}

	@Override
	protected void initContent(final IntegerRange operation) {
		this.startSpinner.setValue(operation.getStart());
		this.endSpinner.setValue(operation.getEnd());
		this.closedCheckBox.setSelected(operation.isClosed());
	}

	@Override
	protected boolean applyContent(final IntegerRange operation) {
		operation.setStart(((Number) this.startSpinner.getValue()).intValue());
		operation.setEnd(((Number) this.endSpinner.getValue()).intValue());
		operation.setClosed(this.closedCheckBox.isSelected());
		return true;
	}
}
