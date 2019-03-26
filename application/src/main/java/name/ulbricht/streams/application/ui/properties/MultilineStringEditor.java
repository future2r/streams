package name.ulbricht.streams.application.ui.properties;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public final class MultilineStringEditor extends JPanel implements PropertyValueEditor {

	private final JTextArea textArea;
	private final JButton editButton;

	public MultilineStringEditor() {
		super(new BorderLayout());

		this.textArea = new JTextArea(1, 50);
		this.textArea.setFont(UIManager.getFont("Label.font"));

		this.editButton = new JButton("\u2026");
		this.editButton.addActionListener(e -> editMultiline());

		add(textArea, BorderLayout.CENTER);
		add(editButton, BorderLayout.EAST);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public Object getValue() {
		return this.textArea.getText();
	}

	@Override
	public void setValue(final Object value) {
		this.textArea.setText(value != null ? (String) value : "");

	}

	private void editMultiline() {
		final var largeTextArea = new JTextArea(10, 70);
		largeTextArea.setText(this.textArea.getText());

		if (JOptionPane.showConfirmDialog(this, new JScrollPane(largeTextArea), "Edit Text",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			this.textArea.setText(largeTextArea.getText());
		}
	}
}
