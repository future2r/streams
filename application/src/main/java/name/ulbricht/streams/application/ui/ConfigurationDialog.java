package name.ulbricht.streams.application.ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import name.ulbricht.streams.application.ui.helper.StreamOperations;
import name.ulbricht.streams.application.ui.properties.PropertiesTable;

@SuppressWarnings("serial")
final class ConfigurationDialog extends JDialog {

	static void showModal(final Window owner, final Object operation) {
		final var dlg = new ConfigurationDialog(owner, operation);
		dlg.setModalityType(ModalityType.APPLICATION_MODAL);
		dlg.setLocationRelativeTo(owner);
		dlg.setVisible(true);
	}

	private final Object operation;

	ConfigurationDialog(final Window owner, final Object operation) {
		super(owner);
		this.operation = operation;

		setTitle(String.format("Configuration of %s", this.operation.getClass().getSimpleName()));

		final var contentPane = new JPanel(new BorderLayout(8, 8));
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
		setContentPane(contentPane);

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

		final var propertiesTable = new PropertiesTable();
		propertiesTable.getModel().updateBean(this.operation,
				StreamOperations.getProperties(this.operation.getClass()));

		contentPane.add(new JScrollPane(propertiesTable), BorderLayout.CENTER);

		final var buttonPanel = new JPanel(new BorderLayout(8, 8));
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		final var closeButton = new JButton("Close");
		closeButton.addActionListener(e -> dispose());
		buttonPanel.add(closeButton, BorderLayout.EAST);

		getRootPane().setDefaultButton(closeButton);
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
}
