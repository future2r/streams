package name.ulbricht.streams.application.ui;

import java.awt.Component;

import javax.swing.JOptionPane;

final class Alerts {

	static void showError(final Component parent, final Throwable ta) {
		showError(parent,  String.format(Messages.getString("Alerts.error.messagePattern"), ta));
		ta.printStackTrace();
	}

	static void showError(final Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, Messages.getString("Alerts.error.title"),
				JOptionPane.ERROR_MESSAGE);
	}

	static void showInfo(final Component parent, final String message) {
		JOptionPane.showMessageDialog(parent, message, Messages.getString("Alerts.info.title"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	private Alerts() {
		// hidden
	}
}
