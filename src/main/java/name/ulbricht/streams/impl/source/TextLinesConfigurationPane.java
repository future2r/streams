package name.ulbricht.streams.impl.source;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class TextLinesConfigurationPane extends AbstractConfigurationPane<TextLines> {

	private JTextArea linesTextArea;

	@Override
	protected void createContent(final Container contentPane) {
		this.linesTextArea = new JTextArea(10, 50);
		this.linesTextArea.setLineWrap(true);
		this.linesTextArea.setWrapStyleWord(true);
		final var linesLabel = new JLabel("Text Lines:");

		contentPane.add(linesLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 0, 4), 0, 0));
		contentPane.add(new JScrollPane(this.linesTextArea), new GridBagConstraints(0, 1, 1, 1, 1, 1,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
	}

	@Override
	protected void initContent(final TextLines operation) {
		this.linesTextArea.setText(operation.getText());
	}

	@Override
	protected boolean applyContent(final TextLines operation) {
		operation.setText(this.linesTextArea.getText());
		return true;
	}
}
