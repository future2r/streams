package name.ulbricht.streams.api;

import java.util.Objects;

public interface StreamOperation {

	String getSourceCode();

	default String getConfigurationText() {
		return null;
	}

	static <T extends StreamOperation> String getName(final T streamOperation) {
		return getName(Objects.requireNonNull(streamOperation, "streamOperation must not be null").getClass());
	}

	static <T extends StreamOperation> String getName(final Class<T> streamOperationClass) {
		final var name = Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null")
				.getAnnotation(Name.class);
		if (name != null)
			return name.value();
		return streamOperationClass.getSimpleName();
	}

	static <T extends StreamOperation> Class<?> getInputType(final T streamOperation) {
		return getInputType(Objects.requireNonNull(streamOperation, "streamOperation must not be null").getClass());
	}

	static <T extends StreamOperation> Class<?> getInputType(final Class<T> streamOperationClass) {
		final var input = Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null")
				.getAnnotation(Input.class);
		if (input != null)
			return input.value();
		return Object.class;
	}

	static <T extends StreamOperation> Class<?> getOutputType(final T streamOperation) {
		return getOutputType(Objects.requireNonNull(streamOperation, "streamOperation must not be null").getClass());
	}

	static <T extends StreamOperation> Class<?> getOutputType(final Class<T> streamOperationClass) {
		final var output = Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null")
				.getAnnotation(Output.class);
		if (output != null)
			return output.value();
		return Object.class;
	}

	static <T extends StreamOperation> String getDisplayName(final T streamOperation) {
		return getDisplayName(Objects.requireNonNull(streamOperation, "streamOperation must not be null").getClass());
	}

	static <T extends StreamOperation> String getDisplayName(final Class<T> streamOperationClass) {
		final var name = StreamOperation
				.getName(Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null"));
		if (StreamSource.class.isAssignableFrom(streamOperationClass)) {
			return String.format("%s (%s)", name, StreamOperation.getOutputType(streamOperationClass).getSimpleName());
		} else if (IntermediateOperation.class.isAssignableFrom(streamOperationClass)) {
			return String.format("%s (%s -> %s)", name,
					StreamOperation.getInputType(streamOperationClass).getSimpleName(),
					StreamOperation.getOutputType(streamOperationClass).getSimpleName());
		} else if (TerminalOperation.class.isAssignableFrom(streamOperationClass)) {
			return String.format("%s (%s)", name, StreamOperation.getInputType(streamOperationClass).getSimpleName());
		}
		throw new IllegalArgumentException("Cannot handle " + streamOperationClass);
	}

	static boolean supportsConfiguration(final StreamOperation streamOperation) {
		return getConfigurations(streamOperation).length > 0;
	}

	static Configuration[] getConfigurations(final StreamOperation streamOperation) {
		Objects.requireNonNull(streamOperation, "streamOperation must not be null");
		final var streamOperationClass = streamOperation.getClass();

		// single annotation
		final var configuration = streamOperationClass.getAnnotation(Configuration.class);
		if (configuration != null)
			return new Configuration[] { configuration };

		// repeated annotations
		final var configurations = streamOperationClass.getAnnotation(Configurations.class);
		if (configurations != null)
			return configurations.value();

		return new Configuration[0];
	}

	static <T extends StreamOperation> T createOperation(final Class<T> streamOperationClass)
			throws StreamOperationException {
		try {
			return Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null")
					.getConstructor((Class[]) null).newInstance((Object[]) null);
		} catch (final ReflectiveOperationException ex) {
			throw new StreamOperationException("Could not create operation from " + streamOperationClass, ex);
		}
	}

	static String quote(final String s) {
		return s.replace("\\", "\\\\");
	}
}
