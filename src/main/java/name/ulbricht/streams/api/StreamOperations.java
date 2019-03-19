package name.ulbricht.streams.api;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
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

	public static boolean supportsConfiguration(final Object streamOperation) {
		return !getConfigurations(streamOperation).isEmpty();
	}

	private static final Map<Class<?>, Map<String, Configuration>> configurationCache = new HashMap<>();

	public static Map<String, Configuration> getConfigurations(final Object streamOperation) {
		Objects.requireNonNull(streamOperation, "streamOperation must not be null");
		final var streamOperationClass = streamOperation.getClass();

		synchronized (configurationCache) {
			final var configurations = configurationCache.get(streamOperationClass);
			if (configurations != null)
				return configurations;
		}

		final Map<String, Configuration> configurations = new HashMap<>();
		try {
			final var propertyDescriptors = Introspector.getBeanInfo(streamOperationClass).getPropertyDescriptors();
			for (final var propertyDescriptor : propertyDescriptors) {
				final var readMethod = propertyDescriptor.getReadMethod();
				if (readMethod != null) {
					final var configurationAnnotation = readMethod.getAnnotation(Configuration.class);
					if (configurationAnnotation != null) {
						configurations.put(propertyDescriptor.getName(), configurationAnnotation);
					}
				}
			}
		} catch (final IntrospectionException ex) {
			throw new StreamOperationException("Could not inspect " + streamOperationClass, ex);
		}

		synchronized (configurationCache) {
			configurationCache.put(streamOperationClass, configurations);
		}
		return configurations;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getConfigurationValue(final String name, final Object streamOperation) {
		try {
			return (T) getPropertyDescriptor(
					Objects.requireNonNull(streamOperation, "streamOperation must not be null").getClass(), name)
							.getReadMethod().invoke(streamOperation, (Object[]) null);
		} catch (final ReflectiveOperationException ex) {
			throw new StreamOperationException("Could not read value for property " + name, ex);
		}
	}

	public static void setConfigurationValue(final String name, final Object streamOperation, final Object value) {
		try {
			getPropertyDescriptor(
					Objects.requireNonNull(streamOperation, "streamOperation must not be null").getClass(), name)
							.getWriteMethod().invoke(streamOperation, value);
		} catch (final ReflectiveOperationException ex) {
			throw new StreamOperationException("Could not write value for property " + name, ex);
		}
	}

	private static PropertyDescriptor getPropertyDescriptor(final Class<?> streamOperationClass, final String name) {
		try {
			return Stream
					.of(Introspector.getBeanInfo(
							Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null"))
							.getPropertyDescriptors()) //
					.filter(pd -> name.equals(pd.getName())) //
					.findFirst().get();
		} catch (final IntrospectionException | NoSuchElementException ex) {
			throw new StreamOperationException("Could not find property " + name, ex);
		}
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
