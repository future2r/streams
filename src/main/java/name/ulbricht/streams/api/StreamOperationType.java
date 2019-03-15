package name.ulbricht.streams.api;

import java.util.function.Function;
import java.util.function.Supplier;

public enum StreamOperationType {

	/**
	 * Marks a stream operations as a stream source. The operation must be a
	 * {@code Supplier<Stream<?>>}.
	 */
	SOURCE,

	/**
	 * Marks a stream operations as a intermediate operation. The operation must be
	 * a {@code Function<Stream<?>, Stream<?>>}.
	 */
	INTERMEDIATE,

	/**
	 * Marks a stream operations as a terminal operation. The operation must be a
	 * {@code Function<Stream<?>, ?>}.
	 */
	TERMINAL;

	void checkClassCompatibility(final Class<?> candidateClass) throws StreamOperationException {

		switch (this) {
		case SOURCE:
			checkImplements(candidateClass, Supplier.class);
			break;
		case INTERMEDIATE:
		case TERMINAL:
			checkImplements(candidateClass, Function.class);
			break;
		}
	}

	private void checkImplements(final Class<?> candidateClass, final Class<?> requiredInterface) {
		if (!requiredInterface.isAssignableFrom(candidateClass))
			throw new StreamOperationException(String.format("Operation of type %s must implement %s", name(),
					requiredInterface.getSimpleName()));
	}
}