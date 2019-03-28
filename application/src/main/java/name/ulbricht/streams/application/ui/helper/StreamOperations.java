package name.ulbricht.streams.application.ui.helper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;
import name.ulbricht.streams.api.Source;
import name.ulbricht.streams.api.StreamOperationException;
import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.Terminal;
import name.ulbricht.streams.api.basic.Empty;
import name.ulbricht.streams.api.basic.SystemOut;

public final class StreamOperations {

	public static StreamOperationSet DEFAULT_PRESET = new StreamOperationSet(new Empty<>(), List.of(),
			new SystemOut<>());

	public static List<Class<?>> findSourceOperations() {
		return ServiceLoader.load(StreamOperationsProvider.class).stream().map(Provider::get)
				.flatMap(StreamOperationsProvider::getSourceOperations)
				.sorted(Comparator.comparing(Class::getSimpleName)).collect(Collectors.toList());
	}

	public static List<Class<?>> findIntermediateOperations() {
		return ServiceLoader.load(StreamOperationsProvider.class).stream().map(Provider::get)
				.flatMap(StreamOperationsProvider::getIntermediateOperations)
				.sorted(Comparator.comparing(Class::getSimpleName)).collect(Collectors.toList());
	}

	public static List<Class<?>> findTerminalOperations() {
		return ServiceLoader.load(StreamOperationsProvider.class).stream().map(Provider::get)
				.flatMap(StreamOperationsProvider::getTerminalOperations)
				.sorted(Comparator.comparing(Class::getSimpleName)).collect(Collectors.toList());
	}

	public static Map<String, Supplier<StreamOperationSet>> findPresets() {
		return Stream
				.concat(Map.<String, Supplier<StreamOperationSet>>of("Empty", () -> DEFAULT_PRESET).entrySet().stream(),
						ServiceLoader.load(StreamOperationsProvider.class).stream().map(Provider::get)
								.flatMap(p -> p.getPresets().entrySet().stream()))
				.collect(
						Collectors.<Map.Entry<String, Supplier<StreamOperationSet>>, String, Supplier<StreamOperationSet>>toMap(
								Map.Entry::getKey, Map.Entry::getValue));
	}

	public static boolean isSourceOperation(final Class<?> streamOperationClass) {
		return isAnnotatedWith(streamOperationClass, Source.class);
	}

	public static boolean isIntermediateOperation(final Class<?> streamOperationClass) {
		return isAnnotatedWith(streamOperationClass, Intermediate.class);
	}

	public static boolean isTerminalOperation(final Class<?> streamOperationClass) {
		return isAnnotatedWith(streamOperationClass, Terminal.class);
	}

	private static boolean isAnnotatedWith(final Class<?> streamOperationClass,
			Class<? extends Annotation> annotationClass) {
		return Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null")
				.getAnnotation(annotationClass) != null;
	}

	public static Object createOperation(final Class<?> streamOperationClass) throws StreamOperationException {
		try {
			return Objects.requireNonNull(streamOperationClass, "streamOperationClass must not be null")
					.getConstructor().newInstance();
		} catch (final ReflectiveOperationException ex) {
			throw new StreamOperationException("Could not create operation from " + streamOperationClass, ex);
		}
	}

	public static String getDescription(final Class<?> streamOperationClass) {
		return getBeanInfo(streamOperationClass).getBeanDescriptor().getShortDescription();
	}

	public static boolean hasProperties(final Class<?> streamOperationClass) {
		return getProperties(streamOperationClass).length > 0;
	}

	public static PropertyDescriptor[] getProperties(final Class<?> streamOperationClass) {
		return Stream.of(getBeanInfo(streamOperationClass).getPropertyDescriptors())
				.filter(p -> !p.getName().equals("class"))
				.filter(pd -> pd.getValue("transient") == null || Boolean.FALSE.equals(pd.getValue("transient")))
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
