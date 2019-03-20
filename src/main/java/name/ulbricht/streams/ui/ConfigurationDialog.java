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
import java.beans.PropertyDescriptor;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
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
				this.operation.getClass().getSimpleName()));

		final var contentPane = new JPanel(new BorderLayout(8, 8));
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
		setContentPane(contentPane);

		final var configurationPanel = new JPanel(new GridBagLayout());
		configurationPanel.setOpaque(false);

		final var description = StreamOperations.getDescription(this.operation.getClass());
		if (description != null) {
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

		Stream.of(StreamOperations.getProperties(this.operation.getClass()))
				.forEach(p -> addConfiguration(configurationPanel, p));

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

	private int addConfiguration(final JPanel panel, final PropertyDescriptor property) {
		final var editorHint = StreamOperations.getEditorHint(property);
		final var editorType = editorHint.map(EditorHint::value).orElse(EditorType.DEFAULT);

		final var label = new JLabel(property.getName() + ':');
		final var description = property.getShortDescription();
		if (description != null)
			label.setToolTipText(description);

		final var type = property.getPropertyType();
		if (type == String.class) {
			if (editorType == EditorType.MULTILINE_TEXT) {
				final var textArea = new JTextArea(7, 80);
				if (description != null)
					textArea.setToolTipText(description);
				textArea.setText(StreamOperations.getPropertyValue(property, this.operation));
				textArea.setCaretPosition(0);
				applyFunctions
						.add(() -> StreamOperations.setPropertyValue(property, this.operation, textArea.getText()));

				return addScrollableComponent(panel, label, textArea);
			}

			final var textField = new JTextField(30);
			if (description != null)
				textField.setToolTipText(description);
			textField.setText(StreamOperations.getPropertyValue(property, this.operation));
			applyFunctions.add(() -> StreamOperations.setPropertyValue(property, this.operation, textField.getText()));

			return addComponent(panel, label, textField);

		} else if (type == Integer.class || type == Integer.TYPE) {
			final var spinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
			if (description != null)
				spinner.setToolTipText(description);
			spinner.setValue(StreamOperations.getPropertyValue(property, this.operation));
			applyFunctions.add(() -> StreamOperations.setPropertyValue(property, this.operation,
					((Number) spinner.getValue()).intValue()));

			return addComponent(panel, label, spinner);
		} else if (type == Long.class || type == Long.TYPE) {
			final var spinner = new JSpinner(new SpinnerNumberModel(0L, Long.MIN_VALUE, Long.MAX_VALUE, 1L));
			if (description != null)
				spinner.setToolTipText(description);
			spinner.setValue(StreamOperations.getPropertyValue(property, this.operation));
			applyFunctions.add(() -> StreamOperations.setPropertyValue(property, this.operation,
					((Number) spinner.getValue()).longValue()));

			return addComponent(panel, label, spinner);

		} else if (type == Double.class || type == Double.TYPE) {
			final var spinner = new JSpinner(
					new SpinnerNumberModel(0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
			if (description != null)
				spinner.setToolTipText(description);
			spinner.setValue(StreamOperations.getPropertyValue(property, this.operation));
			applyFunctions.add(() -> StreamOperations.setPropertyValue(property, this.operation,
					((Number) spinner.getValue()).doubleValue()));

			return addComponent(panel, label, spinner);

		} else if (type == Boolean.class || type == Boolean.TYPE) {
			final var checkBox = new JCheckBox();
			if (description != null)
				checkBox.setToolTipText(description);
			checkBox.setSelected(StreamOperations.getPropertyValue(property, this.operation));
			applyFunctions
					.add(() -> StreamOperations.setPropertyValue(property, this.operation, checkBox.isSelected()));

			return addComponent(panel, label, checkBox);

		} else if (type == Path.class) {
			final var textField = new JTextField(30);
			if (description != null)
				textField.setToolTipText(description);
			textField.setEditable(false);
			final var button = new JButton(Messages.getString("ConfigurationDialog.browse"));

			if (editorType == EditorType.DIRECTORY)
				button.addActionListener(e -> browseDirectory(textField));
			else
				button.addActionListener(e -> browseFile(textField));

			textField.setText(Objects.toString(StreamOperations.getPropertyValue(property, this.operation), ""));
			applyFunctions.add(
					() -> StreamOperations.setPropertyValue(property, this.operation, Paths.get(textField.getText())));

			return addComponent(panel, label, textField, button);

		} else if (type == Charset.class) {
			final var comboBox = new JComboBox<String>(Charset.availableCharsets().keySet().toArray(new String[0]));
			if (description != null)
				comboBox.setToolTipText(description);
			final Charset charset = StreamOperations.getPropertyValue(property, this.operation);
			if (charset != null)
				comboBox.setSelectedItem(charset.name());
			applyFunctions.add(() -> StreamOperations.setPropertyValue(property, this.operation,
					Charset.forName((String) comboBox.getSelectedItem())));

			return addComponent(panel, label, comboBox);

		} else if (type == LocalDate.class) {
			final var textField = new JTextField(10);
			if (description != null)
				textField.setToolTipText(description);
			final LocalDate date = StreamOperations.getPropertyValue(property, this.operation);
			if (date != null)
				textField.setText(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
			applyFunctions.add(() -> StreamOperations.setPropertyValue(property, this.operation,
					LocalDate.parse(textField.getText(), DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))));

			return addComponent(panel, label, textField);

		} else {
			throw new IllegalArgumentException(
					String.format("Cannot handle property '%s' of %s", property.getName(), property.getPropertyType()));
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
