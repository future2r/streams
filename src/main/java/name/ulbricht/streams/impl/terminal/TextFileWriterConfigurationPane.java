package name.ulbricht.streams.impl.terminal;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class TextFileWriterConfigurationPane extends AbstractConfigurationPane<TextFileWriter> {

	private Path currentFile;
	private JTextField fileTextField;

	@Override
	protected void createContent(final Container contentPane) {
		this.fileTextField = new JTextField(30);
		this.fileTextField.setEditable(false);
		final var fileLabel = new JLabel("Current File:");

		final var browseButton = new JButton("Browse...");
		browseButton.addActionListener(e -> browseFile());

		contentPane.add(fileLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 0, 4), 0, 0));
		contentPane.add(this.fileTextField, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(browseButton, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
	}

	private void browseFile() {
		final var fileChooser = new JFileChooser(this.currentFile.getParent().toFile());
		if (fileChooser.showOpenDialog(getComponent()) == JFileChooser.APPROVE_OPTION) {
			final var selectedFile = fileChooser.getSelectedFile();
			if (selectedFile != null) {
				this.currentFile = selectedFile.toPath();
				this.fileTextField.setText(this.currentFile.toString());
			}
		}
	}

	@Override
	protected void initContent(final TextFileWriter operation) {
		this.currentFile = operation.getFile();
		this.fileTextField.setText(this.currentFile.toString());
	}

	@Override
	protected boolean applyContent(final TextFileWriter operation) {
		operation.setFile(this.currentFile);
		return true;
	}
}
