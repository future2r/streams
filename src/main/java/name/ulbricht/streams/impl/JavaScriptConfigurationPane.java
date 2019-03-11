package name.ulbricht.streams.impl;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class JavaScriptConfigurationPane extends AbstractConfigurationPane<JavaScriptOperation> {

	private JTextArea scriptTextArea;

	@Override
	protected void createContent(final Container contentPane) {
		this.scriptTextArea = new JTextArea(15, 75);
		final var scriptLabel = new JLabel("Java Script (in: 'element', out: 'result'):");

		contentPane.add(scriptLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 0, 4), 0, 0));
		contentPane.add(new JScrollPane(this.scriptTextArea), new GridBagConstraints(0, 1, 1, 1, 1, 1,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
	}

	@Override
	protected void initContent(final JavaScriptOperation operation) {
		this.scriptTextArea.setText(operation.getScript());
	}

	@Override
	protected boolean applyContent(final JavaScriptOperation operation) {
		operation.setScript(this.scriptTextArea.getText());
		return true;
	}
}
