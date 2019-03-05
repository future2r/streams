package name.ulbricht.streams.impl.source;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class RandomDoubleGeneratorConfigurationPane extends AbstractConfigurationPane<RandomDoubleGenerator> {

	private JSpinner numberSpinner;
	private JSpinner originSpinner;
	private JSpinner boundSpinner;

	@Override
	protected void createContent(final Container contentPane) {
		this.numberSpinner = new JSpinner(new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1));
		final var numberLabel = new JLabel("Number:");

		this.originSpinner = new JSpinner(
				new SpinnerNumberModel(0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.1));
		final var originLabel = new JLabel("Origin (inclusive):");

		this.boundSpinner = new JSpinner(
				new SpinnerNumberModel(1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.1));
		final var boundLabel = new JLabel("Bound (exclusive):");

		contentPane.add(numberLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.numberSpinner, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		contentPane.add(originLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.originSpinner, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		contentPane.add(boundLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(this.boundSpinner, new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		// somehow the panel wants to span over the whole monitor...
		final var size = contentPane.getPreferredSize();
		contentPane.setPreferredSize(new Dimension(300, size.height));
	}

	@Override
	protected void initContent(RandomDoubleGenerator operation) {
		this.numberSpinner.setValue(operation.getNumber());
		this.originSpinner.setValue(operation.getOrigin());
		this.boundSpinner.setValue(operation.getBound());
	}

	@Override
	protected boolean applyContent(final RandomDoubleGenerator operation) {
		operation.setNumber(((Number) this.numberSpinner.getValue()).intValue());
		operation.setOrigin(((Number) this.originSpinner.getValue()).doubleValue());
		operation.setBound(((Number) this.boundSpinner.getValue()).doubleValue());
		return true;
	}
}
