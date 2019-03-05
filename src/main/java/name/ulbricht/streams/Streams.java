package name.ulbricht.streams;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import name.ulbricht.streams.ui.MainFrame;

public final class Streams {

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			// fall back to the default look and feel
		}

		SwingUtilities.invokeLater(() -> {
			final var mainFrame = new MainFrame();
			mainFrame.setVisible(true);
		});
	}
}
