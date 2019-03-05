package name.ulbricht.streams.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import name.ulbricht.streams.api.ConfigurationPane;
import name.ulbricht.streams.api.StreamOperation;

final class ConfigurationDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	static boolean showModal(final Window owner, final ConfigurationPane<?> configurationPane) {
		final var dlg = new ConfigurationDialog(owner, configurationPane);
		dlg.setModalityType(ModalityType.APPLICATION_MODAL);
		dlg.setLocationRelativeTo(owner);
		dlg.setVisible(true);

		return dlg.getResult();
	}

	private boolean result;
	private final ConfigurationPane<?> configurationPane;

	ConfigurationDialog(final Window owner, final ConfigurationPane<?> configurationPane) {
		super(owner);
		this.configurationPane = configurationPane;

		setTitle(String.format(Messages.getString("ConfigurationDialog.titlePattern"),
				StreamOperation.getDisplayName(this.configurationPane.getOperation())));

		final var contentPane = new JPanel(new BorderLayout(8, 8));
		contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));

		setContentPane(contentPane);

		contentPane.add(this.configurationPane.getComponent(), BorderLayout.CENTER);

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

	boolean getResult() {
		return this.result;
	}

	private void apply() {
		if (this.configurationPane.apply()) {
			this.result = true;
			dispose();
		}
	}

	private void cancel() {
		this.result = false;
		dispose();
	}
}
