package name.ulbricht.streams.application.ui;

import java.io.PrintStream;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

final class TextAreaPrintStream extends PrintStream {

	private final JTextArea textArea;

	TextAreaPrintStream(final PrintStream out, final JTextArea textArea) {
		super(out);
		this.textArea = Objects.requireNonNull(textArea, "textArea must not be null");
	}

	@Override
	public void print(final String s) {
		super.print(s);
		SwingUtilities.invokeLater(() -> this.textArea.append(s + '\n'));
	}
}
