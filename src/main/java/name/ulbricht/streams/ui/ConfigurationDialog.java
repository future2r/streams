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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.api.StreamOperations;

@SuppressWarnings("serial")
final class ConfigurationDialog extends JDialog {

	static boolean showModal(final Window owner, final Object operation) {
		final var dlg = new ConfigurationDialog(owner, operation);
		dlg.setModalityType(ModalityType.APPLICATION_MODAL);
		dlg.setLocationRelativeTo(owner);
		dlg.setVisible(true);

		return dlg.getResult();
	}

	private boolean result;
	private final Object operation;
	private final List<Runnable> applyFunctions = new ArrayList<>();
	private int componentLayoutRow;

	ConfigurationDialog(final Window owner, final Object operation) {
		super(owner);
		this.operation = operation;

		setTitle(String.format(Messages.getString("ConfigurationDialog.titlePattern"),
				StreamOperations.getDisplayName(this.operation.getClass())));

		final var contentPane = new JPanel(new BorderLayout(8, 8));
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
		setContentPane(contentPane);

		final var configurationPanel = new JPanel(new GridBagLayout());
		configurationPanel.setOpaque(false);

		final var description = this.operation.getClass().getAnnotation(StreamOperation.class).description();
		if (!description.isEmpty()) {
			final var descriptionTextArea = new JTextArea(description);
			descriptionTextArea.setLineWrap(true);
			descriptionTextArea.setWrapStyleWord(true);
			descriptionTextArea.setFocusable(false);
			descriptionTextArea.setEditable(false);
			descriptionTextArea.setOpaque(false);
			descriptionTextArea.setFont(UIManager.getFont("Label.font"));

			descriptionTextArea.setBorder(new EmptyBorder(4, 4, 4, 4));
			contentPane.add(descriptionTextArea, BorderLayout.NORTH);
		}

		StreamOperations.getConfigurations(this.operation).entrySet().stream()
				.sorted((e1, e2) -> Integer.compare(e1.getValue().ordinal(), e2.getValue().ordinal()))
				.forEach(e -> addConfiguration(configurationPanel, e.getKey(), e.getValue()));

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
		setSize(getPreferredSize());
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

	private int addConfiguration(final JPanel panel, final String name, final Configuration configuration) {
		var displayName = configuration.displayName();
		if (displayName.isEmpty())
			displayName = name;

		final var label = new JLabel(displayName + ':');

		switch (configuration.type()) {
		case STRING: {
			final var textField = new JTextField(30);
			textField.setText(StreamOperations.getConfigurationValue(name, this.operation));
			applyFunctions.add(() -> StreamOperations.setConfigurationValue(name, this.operation, textField.getText()));

			return addComponent(panel, label, textField);
		}
		case MULTILINE_STRING: {
			final var textArea = new JTextArea(7, 80);
			textArea.setText(StreamOperations.getConfigurationValue(name, this.operation));
			textArea.setCaretPosition(0);
			applyFunctions.add(() -> StreamOperations.setConfigurationValue(name, this.operation, textArea.getText()));

			return addScrollableComponent(panel, label, textArea);
		}
		case INTEGER: {
			final var spinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
			spinner.setValue(StreamOperations.getConfigurationValue(name, this.operation));
			applyFunctions.add(() -> StreamOperations.setConfigurationValue(name, this.operation,
					((Number) spinner.getValue()).intValue()));

			return addComponent(panel, label, spinner);
		}
		case LONG: {
			final var spinner = new JSpinner(new SpinnerNumberModel(0L, Long.MIN_VALUE, Long.MAX_VALUE, 1L));
			spinner.setValue(StreamOperations.getConfigurationValue(name, this.operation));
			applyFunctions.add(() -> StreamOperations.setConfigurationValue(name, this.operation,
					((Number) spinner.getValue()).longValue()));

			return addComponent(panel, label, spinner);
		}
		case DOUBLE: {
			final var spinner = new JSpinner(
					new SpinnerNumberModel(0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
			spinner.setValue(StreamOperations.getConfigurationValue(name, this.operation));
			applyFunctions.add(() -> StreamOperations.setConfigurationValue(name, this.operation,
					((Number) spinner.getValue()).doubleValue()));

			return addComponent(panel, label, spinner);
		}
		case BOOLEAN: {
			final var checkBox = new JCheckBox();
			checkBox.setSelected(StreamOperations.getConfigurationValue(name, this.operation));
			applyFunctions
					.add(() -> StreamOperations.setConfigurationValue(name, this.operation, checkBox.isSelected()));

			return addComponent(panel, label, checkBox);
		}
		case DIRECTORY: {
			final var textField = new JTextField(30);
			textField.setEditable(false);
			final var button = new JButton(Messages.getString("ConfigurationDialog.browse"));
			button.addActionListener(e -> browseDirectory(textField));
			textField.setText(Objects.toString(StreamOperations.getConfigurationValue(name, this.operation), ""));
			applyFunctions.add(
					() -> StreamOperations.setConfigurationValue(name, this.operation, Paths.get(textField.getText())));

			return addComponent(panel, label, textField, button);
		}
		case FILE: {
			final var textField = new JTextField(30);
			textField.setEditable(false);
			final var button = new JButton(Messages.getString("ConfigurationDialog.browse"));
			button.addActionListener(e -> browseFile(textField));
			textField.setText(Objects.toString(StreamOperations.getConfigurationValue(name, this.operation), ""));
			applyFunctions.add(
					() -> StreamOperations.setConfigurationValue(name, this.operation, Paths.get(textField.getText())));

			return addComponent(panel, label, textField, button);
		}
		case ENCODING: {
			final var comboBox = new JComboBox<String>(Charset.availableCharsets().keySet().toArray(new String[0]));
			final Charset charset = StreamOperations.getConfigurationValue(name, this.operation);
			if (charset != null)
				comboBox.setSelectedItem(charset.name());
			applyFunctions.add(() -> StreamOperations.setConfigurationValue(name, this.operation,
					Charset.forName((String) comboBox.getSelectedItem())));

			return addComponent(panel, label, comboBox);
		}
		case LOCAL_DATE: {
			final var textField = new JTextField(10);
			final LocalDate date = StreamOperations.getConfigurationValue(name, this.operation);
			if (date != null)
				textField.setText(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
			applyFunctions.add(() -> StreamOperations.setConfigurationValue(name, this.operation,
					LocalDate.parse(textField.getText(), DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))));

			return addComponent(panel, label, textField);
		}
		default:
			throw new IllegalArgumentException(configuration.type().name());
		}
	}

	private int addComponent(final JPanel panel, final JLabel label, final JComponent component) {
		return addComponent(panel, label, component, null);
	}

	private int addComponent(final JPanel panel, final JLabel label, final JComponent component, final JButton button) {
		panel.add(label, new GridBagConstraints(0, this.componentLayoutRow, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(component, new GridBagConstraints(1, this.componentLayoutRow, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		if (button != null) {
			panel.add(button, new GridBagConstraints(2, this.componentLayoutRow, 1, 1, 0, 0, GridBagConstraints.CENTER,
					GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		}
		return this.componentLayoutRow++;
	}

	private int addScrollableComponent(final JPanel panel, final JLabel label, final JComponent component) {
		panel.add(label, new GridBagConstraints(0, this.componentLayoutRow, 2, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 0, 4), 0, 0));
		panel.add(new JScrollPane(component), new GridBagConstraints(0, this.componentLayoutRow + 1, 2, 1, 1, 1,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		return this.componentLayoutRow += 2;
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
		try {
			this.applyFunctions.forEach(Runnable::run);
			this.result = true;
			dispose();
		} catch (final Exception ex) {
			Alerts.showError(this, ex.toString());
		}
	}

	private void cancel() {
		this.result = false;
		dispose();
	}
}
