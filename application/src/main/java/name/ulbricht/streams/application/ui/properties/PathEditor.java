package name.ulbricht.streams.application.ui.properties;

import java.awt.BorderLayout;
import java.awt.Component;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public final class PathEditor extends JPanel implements PropertyValueEditor {

	private final JTextField textField;
	private final JButton browseButton;
	private Path value;

	public PathEditor() {
		super(new BorderLayout());

		this.textField = new JTextField();
		this.textField.setEditable(false);

		this.browseButton = new JButton("\u2026");
		this.browseButton.addActionListener(e -> browse());

		add(textField, BorderLayout.CENTER);
		add(browseButton, BorderLayout.EAST);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public void setValue(final Object value) {
		this.value = (Path) value;
		this.textField.setText(this.value != null ? this.value.toString() : "");

	}

	private void browse() {
		final var fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(this.value != null ? this.value.toFile() : null);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			final var selected = fileChooser.getSelectedFile();
			if (selected != null) {
				setValue(selected.toPath());
			}
		}
	}
}
