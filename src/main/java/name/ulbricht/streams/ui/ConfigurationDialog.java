package name.ulbricht.streams.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;

final class ConfigurationDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	static boolean showModal(final Window owner, final StreamOperation operation) {
		final var dlg = new ConfigurationDialog(owner, operation);
		dlg.setModalityType(ModalityType.APPLICATION_MODAL);
		dlg.setLocationRelativeTo(owner);
		dlg.setVisible(true);

		return dlg.getResult();
	}

	private boolean result;
	private final StreamOperation operation;
	private final List<Runnable> applyFunctions = new ArrayList<>();

	ConfigurationDialog(final Window owner, final StreamOperation operation) {
		super(owner);
		this.operation = operation;

		setTitle(String.format(Messages.getString("ConfigurationDialog.titlePattern"),
				StreamOperation.getDisplayName(this.operation)));

		final var contentPane = new JPanel(new BorderLayout(8, 8));
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
		setContentPane(contentPane);

		final var configurationPanel = new JPanel(new GridBagLayout());
		configurationPanel.setOpaque(false);

		final var configurations = StreamOperation.getConfigurations(this.operation);
		var row = 0;
		for (final var config : configurations) {
			row = addConfiguration(row, configurationPanel, config);
		}

		contentPane.add(configurationPanel, BorderLayout.CENTER);

		final var bottomPanel = new JPanel(new BorderLayout(8, 8));
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		final var buttonPanel = new JPanel(new GridLayout(1, 0, 4, 4));
		bottomPanel.add(buttonPanel, BorderLayout.EAST);

		final var okButton = new JButton("OK");
		okButton.addActionListener(e -> apply());
		getRootPane().setDefaultButton(okButton);
		buttonPanel.add(okButton);

		final var cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> cancel());
		buttonPanel.add(cancelButton);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				ConfigurationDialog.this.result = false;
			}
		});

		pack();
	}

	private int addConfiguration(final int row, final JPanel panel, final Configuration configuration) {

		var displayName = configuration.displayName();
		if (displayName.isEmpty())
			displayName = configuration.name();

		final var label = new JLabel(displayName + ':');

		final var description = configuration.description();
		if (!description.isEmpty()) {
			label.setToolTipText(String.format("<html><p width='300'>%s</p></html>", description));
			label.setIcon(Images.getSmallIcon(Images.INFO));
			label.setHorizontalTextPosition(JLabel.LEFT);
		}

		switch (configuration.type()) {
		case STRING: {
			panel.add(label, new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

			final var textField = new JTextField(30);
			panel.add(textField, new GridBagConstraints(1, row, 1, 1, 1, 0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
			textField.setText(StreamOperation.getConfigurationValue(this.operation, configuration));
			applyFunctions.add(
					() -> StreamOperation.setConfigurationValue(this.operation, configuration, textField.getText()));

			return row + 1;
		}
		case MULTILINE_STRING: {
			panel.add(label, new GridBagConstraints(0, row, 2, 1, 0, 0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(4, 4, 0, 4), 0, 0));

			final var textArea = new JTextArea(10, 50);
			panel.add(new JScrollPane(textArea), new GridBagConstraints(0, row + 1, 2, 1, 1, 1, GridBagConstraints.WEST,
					GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
			textArea.setText(StreamOperation.getConfigurationValue(this.operation, configuration));
			applyFunctions.add(
					() -> StreamOperation.setConfigurationValue(this.operation, configuration, textArea.getText()));

			return row + 2;
		}
		case INTEGER: {
			panel.add(label, new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

			final var spinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
			panel.add(spinner, new GridBagConstraints(1, row, 1, 1, 1, 0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
			spinner.setValue(StreamOperation.getConfigurationValue(this.operation, configuration));
			applyFunctions.add(
					() -> StreamOperation.setConfigurationValue(this.operation, configuration, spinner.getValue()));

			return row + 1;
		}
		case DOUBLE: {
			panel.add(label, new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

			final var spinner = new JSpinner(
					new SpinnerNumberModel(0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
			panel.add(spinner, new GridBagConstraints(1, row, 1, 1, 1, 0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
			spinner.setValue(StreamOperation.getConfigurationValue(this.operation, configuration));
			applyFunctions.add(
					() -> StreamOperation.setConfigurationValue(this.operation, configuration, spinner.getValue()));

			return row + 1;
		}
		case BOOLEAN: {
			panel.add(label, new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

			final var checkBox = new JCheckBox();
			panel.add(checkBox, new GridBagConstraints(1, row, 1, 1, 0, 0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
			checkBox.setSelected(StreamOperation.getConfigurationValue(this.operation, configuration));
			applyFunctions.add(
					() -> StreamOperation.setConfigurationValue(this.operation, configuration, checkBox.isSelected()));

			return row + 1;
		}
		case DIRECTORY: {
			panel.add(label, new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

			final var textField = new JTextField(30);
			textField.setEditable(false);
			panel.add(textField, new GridBagConstraints(1, row, 1, 1, 1, 0, GridBagConstraints.WEST,
					GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));

			final var browseButton = new JButton("Browse...");
			browseButton.addActionListener(e -> browseDirectory(textField));
			panel.add(browseButton, new GridBagConstraints(2, row, 1, 1, 0, 0, GridBagConstraints.CENTER,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
			textField.setText(
					Objects.toString(StreamOperation.getConfigurationValue(this.operation, configuration), ""));
			applyFunctions.add(() -> StreamOperation.setConfigurationValue(this.operation, configuration,
					Paths.get(textField.getText())));

			return row + 1;
		}
		case FILE: {
			panel.add(label, new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

			final var textField = new JTextField(30);
			textField.setEditable(false);
			panel.add(textField, new GridBagConstraints(1, row, 1, 1, 1, 0, GridBagConstraints.WEST,
					GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));

			final var browseButton = new JButton(Messages.getString("ConfigurationDialog.browse"));
			browseButton.addActionListener(e -> browseFile(textField));
			panel.add(browseButton, new GridBagConstraints(2, row, 1, 1, 0, 0, GridBagConstraints.CENTER,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
			textField.setText(
					Objects.toString(StreamOperation.getConfigurationValue(this.operation, configuration), ""));
			applyFunctions.add(() -> StreamOperation.setConfigurationValue(this.operation, configuration,
					Paths.get(textField.getText())));

			return row + 1;
		}
		default:
			throw new IllegalArgumentException(configuration.type().name());
		}
	}

	private void browseDirectory(final JTextField textField) {
		final var s = textField.getText();
		final var currentDirectory = s != null && !s.isEmpty() ? Paths.get(s) : null;

		final var fileChooser = new JFileChooser();
		if (currentDirectory != null)
			fileChooser.setCurrentDirectory(currentDirectory.toFile());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fileChooser.showDialog(this.getContentPane(),
				Messages.getString("ConfigurationDialog.selectDirectory")) == JFileChooser.APPROVE_OPTION) {
			final var selectedDirectory = fileChooser.getSelectedFile();
			if (selectedDirectory != null) {
				textField.setText(selectedDirectory.toString());
			}
		}
	}

	private void browseFile(final JTextField textField) {
		final var s = textField.getText();
		final var currentDirectory = s != null && !s.isEmpty() ? Paths.get(s).getParent() : null;

		final var fileChooser = new JFileChooser();
		if (currentDirectory != null)
			fileChooser.setCurrentDirectory(currentDirectory.toFile());

		if (fileChooser.showDialog(this.getContentPane(),
				Messages.getString("ConfigurationDialog.selectFile")) == JFileChooser.APPROVE_OPTION) {
			final var selectedFile = fileChooser.getSelectedFile();
			if (selectedFile != null) {
				textField.setText(selectedFile.toString());
			}
		}
	}

	boolean getResult() {
		return this.result;
	}

	private void apply() {
		this.applyFunctions.forEach(Runnable::run);
		this.result = true;
		dispose();
	}

	private void cancel() {
		this.result = false;
		dispose();
	}
}
