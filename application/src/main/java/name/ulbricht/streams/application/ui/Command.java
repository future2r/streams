package name.ulbricht.streams.application.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

enum Command {

	EXIT("Exit"), ABOUT("About\u2026"),

	EXECUTE("Execute", "execution"), INTERRUPT("Interrupt", "interrupt"),

	SOURCE_CONFIGURE("Configure\u2026"),

	INTERMEDIATE_ADD("Add"), INTERMEDIATE_MOVE_UP("Move Up"), INTERMEDIATE_MOVE_DOWN("Move Down"),
	INTERMEDIATE_REMOVE("Remove"), INTERMEDIATE_REMOVE_ALL("Remove All"), INTERMEDIATE_CONFIGURE("Configure\u2026"),

	TERMINAL_CONFIGURE("Configure\u2026"),

	COPY_CODE("Copy to Clipboard", "copy");

	private final String displayName;
	private final String iconBaseName;

	private Command(final String displayName) {
		this(displayName, null);
	}

	private Command(final String displayName, final String iconBaseName) {
		this.displayName = displayName;
		this.iconBaseName = iconBaseName;
	}

	String getDisplayName() {
		return this.displayName;
	}

	Optional<Icon> getSmallIcon() {
		return Icons.getIcon(this.iconBaseName, Icons.Size.X_SMALL);
	}

	Optional<Icon> getLargeIcon() {
		return Icons.getIcon(this.iconBaseName, Icons.Size.MEDIUM);
	}

	Action action(final Runnable runnable) {
		return action(runnable != null ? e -> runnable.run() : null);
	}

	Action action(final ActionListener actionListener) {
		return action(actionListener, null);
	}

	Action action(final Runnable runnable, final BooleanSupplier validator) {
		return action(runnable != null ? e -> runnable.run() : null, validator);
	}

	Action action(final ActionListener actionListener, final BooleanSupplier validator) {
		final var action = new ActionImpl(actionListener, validator);
		action.putValue(Action.ACTION_COMMAND_KEY, name());
		action.putValue(Action.NAME, getDisplayName());
		getSmallIcon().ifPresent(icon -> action.putValue(Action.SMALL_ICON, icon));
		getLargeIcon().ifPresent(icon -> action.putValue(Action.LARGE_ICON_KEY, icon));
		return action;
	}
}

final class ActionImpl extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final ActionListener actionListener;
	private final BooleanSupplier validator;

	ActionImpl(final ActionListener actionListener, final BooleanSupplier validator) {
		this.actionListener = Objects.requireNonNull(actionListener, "actionListener must not be null");
		this.validator = validator;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		this.actionListener.actionPerformed(e);
	}

	void validate() {
		if (this.validator != null)
			setEnabled(this.validator.getAsBoolean());
	}
}