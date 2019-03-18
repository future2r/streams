package name.ulbricht.streams.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.stream.Stream;

import javax.swing.AbstractAction;
import javax.swing.Action;

final class Actions {

	private static final class ActionImpl extends AbstractAction {

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

	static Action action(final String commandKey, final Runnable runnable) {
		return action(commandKey, runnable, null);
	}

	static Action action(final String commandKey, final ActionListener actionListener) {
		return action(commandKey, actionListener, null);
	}

	static Action action(final String commandKey, final Runnable runnable, final BooleanSupplier validator) {
		return action(commandKey, runnable != null ? e -> runnable.run() : null, validator);
	}

	static Action action(final String commandKey, final ActionListener actionListener,
			final BooleanSupplier validator) {
		final var action = new ActionImpl(actionListener, validator);
		action.putValue(Action.ACTION_COMMAND_KEY, commandKey);
		action.putValue(Action.NAME, Messages.getString("action." + commandKey + ".name"));

		action.putValue(Action.SMALL_ICON,
				Icons.getSmallIcon(Messages.getString("action." + commandKey + ".smallIcon")));
		action.putValue(Action.LARGE_ICON_KEY,
				Icons.getSmallIcon(Messages.getString("action." + commandKey + ".largeIcon")));

		action.putValue(Action.SHORT_DESCRIPTION, Messages.getString("action." + commandKey + ".shortDescription"));

		return action;
	}

	static Actions of(final Action... actions) {
		return new Actions(actions);
	}

	static void validate(final Action action) {
		if (action instanceof ActionImpl) {
			final var actionImpl = (ActionImpl) action;
			actionImpl.validate();
		}
	}

	private final Map<String, Action> actions = new HashMap<>();

	private Actions(final Action... actions) {
		Stream.of(actions).forEach(this::add);
	}

	Action get(final String commandKey) {
		return this.actions.get(commandKey);
	}

	Action add(final Action action) {
		this.actions.put((String) action.getValue(Action.ACTION_COMMAND_KEY), action);
		return action;
	}

	void validate() {
		this.actions.values().stream() //
				.filter(a -> a instanceof ActionImpl) //
				.map(a -> (ActionImpl) a) //
				.forEach(ActionImpl::validate);
	}
}
