package name.ulbricht.streams.api;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public abstract class AbstractConfigurationPane<T extends StreamOperation> implements ConfigurationPane<T> {

	private final Container contentPane;
	private T operation;

	public AbstractConfigurationPane() {
		this.contentPane = new JPanel();
		this.contentPane.setLayout(new GridBagLayout());

		createContent(this.contentPane);
	}

	protected abstract void createContent(final Container contentPane);

	@Override
	public final Component getComponent() {
		return this.contentPane;
	}

	@Override
	public final void setOperation(final T operation) {
		this.operation = operation;
		initContent(this.operation);
	}

	protected abstract void initContent(final T operation);

	@Override
	public final T getOperation() {
		return this.operation;
	}

	@Override
	public final boolean apply() {
		return applyContent(this.operation);
	}

	protected abstract boolean applyContent(final T operation);
}
