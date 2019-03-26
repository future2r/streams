package name.ulbricht.streams.application.ui;

import java.awt.Component;

import javax.swing.JOptionPane;

final class Alerts {

	static void showError(final Component parent, final Throwable ta) {
		showError(parent, String.format("An error has occurred:%n%s", ta));
		ta.printStackTrace();
	}

	static void showError(final Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	static void showInfo(final Component parent, final String message) {
		JOptionPane.showMessageDialog(parent, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	private Alerts() {
		// hidden
	}
}
