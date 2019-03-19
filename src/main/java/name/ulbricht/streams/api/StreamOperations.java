package name.ulbricht.streams.api;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public final class StreamOperations {

	public static Object createOperation(final Class<?> streamOperationClass) throws StreamOperationException {
		try {
			return Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null")
					.getConstructor((Class[]) null).newInstance((Object[]) null);
		} catch (final ReflectiveOperationException ex) {
			throw new StreamOperationException("Could not create operation from " + streamOperationClass, ex);
		}
	}

	public static String getDisplayName(final Class<?> streamOperationClass) {
		final var beanInfo = getBeanInfo(streamOperationClass);

		final var streamOperationAnnotation = Objects
				.requireNonNull(streamOperationClass, "streamOperationClass must not be null")
				.getAnnotation(StreamOperation.class);

		final var name = beanInfo.getBeanDescriptor().getName();
		final var input = streamOperationAnnotation.input().getSimpleName();
		final var output = streamOperationAnnotation.output().getSimpleName();

		switch (streamOperationAnnotation.type()) {
		case SOURCE:
			return String.format("%s (%s)", name, output);
		case INTERMEDIATE:
			return String.format("%s (%s -> %s)", name, input, output);
		case TERMINAL:
			return String.format("%s (%s)", name, input);
		}

		throw new IllegalArgumentException("Unsupported operation type: " + streamOperationAnnotation.type());
	}

	public static String getDescription(final Class<?> streamOperationClass) {
		return getBeanInfo(streamOperationClass).getBeanDescriptor().getShortDescription();
	}

	public static boolean hasProperties(final Class<?> streamOperationClass) {
		return getProperties(streamOperationClass).length > 0;
	}

	public static PropertyDescriptor[] getProperties(final Class<?> streamOperationClass) {
		final var beanInfo = getBeanInfo(streamOperationClass);
		return Stream.of(beanInfo.getPropertyDescriptors()).filter(p -> !p.getName().equals("class"))
				.toArray(PropertyDescriptor[]::new);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getPropertyValue(final PropertyDescriptor property, final Object streamOperation) {
		try {
			return (T) property.getReadMethod().invoke(streamOperation, (Object[]) null);
		} catch (final ReflectiveOperationException ex) {
			throw new StreamOperationException("Could not read value for property " + property.getName(), ex);
		}
	}

	public static void setPropertyValue(final PropertyDescriptor property, final Object streamOperation,
			final Object value) {
		try {
			property.getWriteMethod().invoke(streamOperation, value);
		} catch (final ReflectiveOperationException ex) {
			throw new StreamOperationException("Could not write value for property " + property.getName(), ex);
		}
	}

	public static Optional<EditorHint> getEditorHint(final PropertyDescriptor property) {
		return Optional.ofNullable(property.getReadMethod().getAnnotation(EditorHint.class));
	}

	@SuppressWarnings("unchecked")
	public static Class<?>[] findOperations(final StreamOperationType streamOperationType) {
		final var module = ModuleLayer.boot().configuration().modules().stream()
				.filter(m -> m.name().equals("name.ulbricht.streams")).findFirst().get();

		try (final var moduleReader = module.reference().open()) {
			return (Class<?>[]) moduleReader.list() //
					.filter(s -> s.startsWith("name/ulbricht/streams/impl/") && s.endsWith(".class")) //
					.map(s -> s.substring(0, s.length() - 6).replace('/', '.')) //
					.map(StreamOperations::loadClass) //
					.filter(Optional::isPresent) //
					.map(Optional::get) //
					.filter(c -> isCompatible(c, streamOperationType)) //
					.sorted(Comparator
							.comparing(c -> StreamOperations.getDisplayName((Class<? extends StreamOperations>) c))) //
					.toArray(Class<?>[]::new);
		} catch (final IOException ex) {
			throw new StreamOperationException(ex);
		}
	}

	private static boolean isCompatible(final Class<?> candidateClass, final StreamOperationType streamOperationType) {
		final var streamOperationAnnotation = candidateClass.getAnnotation(StreamOperation.class);
		if (streamOperationAnnotation != null && streamOperationAnnotation.type() == streamOperationType) {
			streamOperationAnnotation.type().checkClassCompatibility(candidateClass);
			return true;
		}
		return false;
	}

	private static Optional<Class<?>> loadClass(final String className) {
		try {
			return Optional.of(Class.forName(className));
		} catch (ClassNotFoundException e) {
			return Optional.empty();
		}
	}

	private static BeanInfo getBeanInfo(final Class<?> streamOperationClass) {
		try {
			return Introspector
					.getBeanInfo(Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null"));
		} catch (final IntrospectionException ex) {
			throw new StreamOperationException("Could not load bean info for " + streamOperationClass, ex);
		}
	}

	public StreamOperations() {
		// hidden
	}
}
