package name.ulbricht.streams.ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;

@SuppressWarnings("serial")
final class ConfigurationDialog extends JDialog {

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

		final var description = StreamOperation.getDescription(operation);
		if (description != null) {
			final var descriptionLabel = new JLabel(
					String.format("<html><p style='width: auto'>%s</p></html>", description));
			descriptionLabel.setBorder(new EmptyBorder(4, 4, 4, 4));
			contentPane.add(descriptionLabel, BorderLayout.NORTH);
		}

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

		final var okButton = new JButton(Messages.getString("ConfigurationDialog.okButton"));
		okButton.addActionListener(e -> apply());
		getRootPane().setDefaultButton(okButton);
		buttonPanel.add(okButton);

		final var cancelButton = new JButton(Messages.getString("ConfigurationDialog.cancelButton"));
		cancelButton.addActionListener(e -> cancel());
		buttonPanel.add(cancelButton);

		getRootPane().setDefaultButton(okButton);
		registerKeyStrokeEvent("WindowClosing", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

		pack();
	}

	private void registerKeyStrokeEvent(final String key, final KeyStroke keyStroke, final AWTEvent event) {
		final var actionKey = "name.ulbricht.streams.ui.dispatch:" + key;
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, actionKey);
		getRootPane().getActionMap().put(actionKey, new AbstractAction() {
			public void actionPerformed(final ActionEvent e) {
				ConfigurationDialog.this.dispatchEvent(event);
			}
		});
	}

	private int addConfiguration(final int row, final JPanel panel, final Configuration configuration) {
		var displayName = configuration.displayName();
		if (displayName.isEmpty())
			displayName = configuration.name();

		final var label = new JLabel(displayName + ':');

		switch (configuration.type()) {
		case STRING: {
			final var textField = new JTextField(30);
			textField.setText(StreamOperation.getConfigurationValue(this.operation, configuration));
			applyFunctions.add(
					() -> StreamOperation.setConfigurationValue(this.operation, configuration, textField.getText()));

			return addComponent(panel, row, label, textField);
		}
		case MULTILINE_STRING: {
			final var textArea = new JTextArea(10, 50);
			textArea.setText(StreamOperation.getConfigurationValue(this.operation, configuration));
			textArea.setCaretPosition(0);
			applyFunctions.add(
					() -> StreamOperation.setConfigurationValue(this.operation, configuration, textArea.getText()));

			return addScrollableComponent(panel, row, label, textArea);
		}
		case INTEGER: {
			final var spinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
			spinner.setValue(StreamOperation.getConfigurationValue(this.operation, configuration));
			applyFunctions.add(() -> StreamOperation.setConfigurationValue(this.operation, configuration,
					((Number) spinner.getValue()).intValue()));

			return addComponent(panel, row, label, spinner);
		}
		case LONG: {
			final var spinner = new JSpinner(new SpinnerNumberModel(0L, Long.MIN_VALUE, Long.MAX_VALUE, 1L));
			spinner.setValue(StreamOperation.getConfigurationValue(this.operation, configuration));
			applyFunctions.add(() -> StreamOperation.setConfigurationValue(this.operation, configuration,
					((Number) spinner.getValue()).longValue()));

			return addComponent(panel, row, label, spinner);
		}
		case DOUBLE: {
			final var spinner = new JSpinner(
					new SpinnerNumberModel(0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
			spinner.setValue(StreamOperation.getConfigurationValue(this.operation, configuration));
			applyFunctions.add(() -> StreamOperation.setConfigurationValue(this.operation, configuration,
					((Number) spinner.getValue()).doubleValue()));

			return addComponent(panel, row, label, spinner);
		}
		case BOOLEAN: {
			final var checkBox = new JCheckBox();
			checkBox.setSelected(StreamOperation.getConfigurationValue(this.operation, configuration));
			applyFunctions.add(
					() -> StreamOperation.setConfigurationValue(this.operation, configuration, checkBox.isSelected()));

			return addComponent(panel, row, label, checkBox);
		}
		case DIRECTORY: {
			final var textField = new JTextField(30);
			textField.setEditable(false);
			final var button = new JButton(Messages.getString("ConfigurationDialog.browse"));
			button.addActionListener(e -> browseDirectory(textField));
			textField.setText(
					Objects.toString(StreamOperation.getConfigurationValue(this.operation, configuration), ""));
			applyFunctions.add(() -> StreamOperation.setConfigurationValue(this.operation, configuration,
					Paths.get(textField.getText())));

			return addComponent(panel, row, label, textField, button);
		}
		case FILE: {
			final var textField = new JTextField(30);
			textField.setEditable(false);
			final var button = new JButton(Messages.getString("ConfigurationDialog.browse"));
			button.addActionListener(e -> browseFile(textField));
			textField.setText(
					Objects.toString(StreamOperation.getConfigurationValue(this.operation, configuration), ""));
			applyFunctions.add(() -> StreamOperation.setConfigurationValue(this.operation, configuration,
					Paths.get(textField.getText())));

			return addComponent(panel, row, label, textField, button);
		}
		case ENCODING: {
			final var comboBox = new JComboBox<String>(Charset.availableCharsets().keySet().toArray(new String[0]));
			final Charset charset = StreamOperation.getConfigurationValue(this.operation, configuration);
			if (charset != null)
				comboBox.setSelectedItem(charset.name());
			applyFunctions.add(() -> StreamOperation.setConfigurationValue(this.operation, configuration,
					Charset.forName((String) comboBox.getSelectedItem())));

			return addComponent(panel, row, label, comboBox);
		}
		default:
			throw new IllegalArgumentException(configuration.type().name());
		}
	}

	private int addComponent(final JPanel panel, final int row, final JLabel label, final JComponent component) {
		return addComponent(panel, row, label, component, null);
	}

	private int addComponent(final JPanel panel, final int row, final JLabel label, final JComponent component,
			final JButton button) {
		panel.add(label, new GridBagConstraints(0, row, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(4, 4, 4, 4), 0, 0));
		panel.add(component, new GridBagConstraints(1, row, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		if (button != null) {
			panel.add(button, new GridBagConstraints(2, row, 1, 1, 0, 0, GridBagConstraints.CENTER,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		}
		return row + 1;
	}

	private int addScrollableComponent(final JPanel panel, final int row, final JLabel label,
			final JComponent component) {
		panel.add(label, new GridBagConstraints(0, row, 2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(4, 4, 0, 4), 0, 0));
		panel.add(new JScrollPane(component), new GridBagConstraints(0, row + 1, 2, 1, 1, 1, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		return row + 2;
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
