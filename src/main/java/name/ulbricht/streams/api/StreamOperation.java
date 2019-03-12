package name.ulbricht.streams.api;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public interface StreamOperation {

	String getSourceCode();

	default String getConfigurationText() {
		return null;
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

	static <T extends StreamOperation> String getDisplayName(final T streamOperation) {
		return getDisplayName(Objects.requireNonNull(streamOperation, "streamOperation must not be null").getClass());
	}

	static <T extends StreamOperation> String getDisplayName(final Class<T> streamOperationClass) {
		final var operation = Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null")
				.getAnnotation(Operation.class);

		var name = operation != null ? operation.name() : "";
		if (name.isEmpty())
			name = streamOperationClass.getSimpleName();

		final var input = operation != null ? operation.input().getSimpleName() : Object.class.getSimpleName();
		final var output = operation != null ? operation.output().getSimpleName() : Object.class.getSimpleName();

		if (SourceOperation.class.isAssignableFrom(streamOperationClass)) {
			return String.format("%s (%s)", name, output);
		} else if (IntermediateOperation.class.isAssignableFrom(streamOperationClass)) {
			return String.format("%s (%s -> %s)", name, input, output);
		} else if (TerminalOperation.class.isAssignableFrom(streamOperationClass)) {
			return String.format("%s (%s)", name, input);
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

	@SuppressWarnings("unchecked")
	static <T> T getConfigurationValue(final StreamOperation streamOperation, final Configuration configuration)
			throws StreamOperationException {
		try {
			return (T) getPropertyDescriptor(
					Objects.requireNonNull(streamOperation, "streamOperation must not be null").getClass(),
					configuration).getReadMethod().invoke(streamOperation, (Object[]) null);
		} catch (final ReflectiveOperationException ex) {
			throw new StreamOperationException("Could not read value for property " + configuration.name(), ex);
		}
	}

	static void setConfigurationValue(final StreamOperation streamOperation, final Configuration configuration,
			final Object value) throws StreamOperationException {
		try {
			getPropertyDescriptor(
					Objects.requireNonNull(streamOperation, "streamOperation must not be null").getClass(),
					configuration).getWriteMethod().invoke(streamOperation, value);
		} catch (final ReflectiveOperationException ex) {
			throw new StreamOperationException("Could not write value for property " + configuration.name(), ex);
		}
	}

	private static PropertyDescriptor getPropertyDescriptor(final Class<? extends StreamOperation> streamOperationClass,
			final Configuration configuration) throws StreamOperationException {
		try {
			return Stream
					.of(Introspector.getBeanInfo(
							Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null"))
							.getPropertyDescriptors()) //
					.filter(pd -> configuration.name().equals(pd.getName())) //
					.findFirst().get();
		} catch (final IntrospectionException | NoSuchElementException ex) {
			throw new StreamOperationException("Could not find property " + configuration.name(), ex);
		}
	}

	static String quote(final String s) {
		return s.replace("\\", "\\\\");
	}

	@SuppressWarnings("unchecked")
	static Class<? extends SourceOperation<?>>[] findSourceOperations() {
		return (Class<? extends SourceOperation<?>>[]) findOperations(SourceOperation.class);
	}

	@SuppressWarnings("unchecked")
	static Class<? extends IntermediateOperation<?, ?>>[] findIntermediateOperations() {
		return (Class<? extends IntermediateOperation<?, ?>>[]) findOperations(IntermediateOperation.class);
	}

	@SuppressWarnings("unchecked")
	static Class<? extends TerminalOperation<?>>[] findTerminalOperations() {
		return (Class<? extends TerminalOperation<?>>[]) findOperations(TerminalOperation.class);
	}

	@SuppressWarnings("unchecked")
	static <T> Class<? extends T>[] findOperations(final Class<T> operationInterface) {
		final var module = ModuleLayer.boot().configuration().modules().stream()
				.filter(m -> m.name().equals("name.ulbricht.streams")).findFirst().get();

		try (final var moduleReader = module.reference().open()) {
			return (Class<T>[]) moduleReader.list() //
					.filter(s -> s.startsWith("name/ulbricht/streams/impl/") && s.endsWith(".class")) //
					.map(s -> s.substring(0, s.length() - 6).replace('/', '.')) //
					.map(StreamOperation::loadClass) //
					.filter(Optional::isPresent) //
					.map(Optional::get) //
					.filter(operationInterface::isAssignableFrom) //
					.sorted(Comparator
							.comparing(c -> StreamOperation.getDisplayName((Class<? extends StreamOperation>) c))) //
					.toArray(Class<?>[]::new);
		} catch (final IOException ex) {
			throw new StreamOperationException(ex);
		}
	}

	private static Optional<Class<?>> loadClass(final String className) {
		try {
			return Optional.of(Class.forName(className));
		} catch (ClassNotFoundException e) {
			return Optional.empty();
		}
	}
}
