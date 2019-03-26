package name.ulbricht.streams.application.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.Action;

final class Actions {

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
		this.actions.values().stream().filter(a -> a instanceof ActionImpl).map(a -> (ActionImpl) a)
				.forEach(ActionImpl::validate);
	}
}
