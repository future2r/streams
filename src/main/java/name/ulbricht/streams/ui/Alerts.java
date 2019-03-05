package name.ulbricht.streams.ui;

import java.awt.Component;

import javax.swing.JOptionPane;

final class Alerts {

	static void showError(final Component parent, final Throwable ta) {
		JOptionPane.showMessageDialog(parent, String.format(Messages.getString("Alerts.error.messagePattern"), ta),
				Messages.getString("Alerts.error.title"), JOptionPane.ERROR_MESSAGE);
		ta.printStackTrace();
	}

	static void showInfo(final Component parent, final String message) {
		JOptionPane.showMessageDialog(parent, message, Messages.getString("Alerts.info.title"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	private Alerts() {
		// hidden
	}
}
