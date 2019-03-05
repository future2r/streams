package name.ulbricht.streams.ui;

import java.util.Objects;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

final class TextAreaLogHandler extends Handler {

	private final JTextArea textArea;

	TextAreaLogHandler(final JTextArea textArea) {
		this.textArea = Objects.requireNonNull(textArea, "textArea must not be null");
	}

	@Override
	public void publish(final LogRecord record) {
		SwingUtilities.invokeLater(() -> this.textArea.append(record.getMessage() + '\n'));
	}

	@Override
	public void flush() {
		// nothing to do here
	}

	@Override
	public void close() throws SecurityException {
		// nothing to do here
	}
}