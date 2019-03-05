package name.ulbricht.streams.impl.source;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import name.ulbricht.streams.api.AbstractConfigurationPane;

public final class FindFilesConfigurationPane extends AbstractConfigurationPane<FindFiles> {

	private Path currentdirectory;
	private JTextField directoryTextField;

	@Override
	protected void createContent(final Container contentPane) {
		this.directoryTextField = new JTextField(30);
		this.directoryTextField.setEditable(false);
		final var directoryLabel = new JLabel("Current Directory:");

		final var browseButton = new JButton("Browse...");
		browseButton.addActionListener(e -> browseFile());

		contentPane.add(directoryLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 0, 4), 0, 0));
		contentPane.add(this.directoryTextField, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		contentPane.add(browseButton, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
	}

	private void browseFile() {
		final var fileChooser = new JFileChooser(this.currentdirectory.toFile());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fileChooser.showOpenDialog(getComponent()) == JFileChooser.APPROVE_OPTION) {
			final var selectedDirectory = fileChooser.getSelectedFile();
			if (selectedDirectory != null) {
				this.currentdirectory = selectedDirectory.toPath();
				this.directoryTextField.setText(this.currentdirectory.toString());
			}
		}
	}

	@Override
	protected void initContent(final FindFiles operation) {
		this.currentdirectory = operation.getDirectory();
		this.directoryTextField.setText(this.currentdirectory.toString());
	}

	@Override
	protected boolean applyContent(final FindFiles operation) {
		operation.setDirectory(this.currentdirectory);
		return true;
	}
}
