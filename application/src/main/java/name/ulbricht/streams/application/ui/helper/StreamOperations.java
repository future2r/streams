package name.ulbricht.streams.application.ui.helper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;
import name.ulbricht.streams.api.Source;
import name.ulbricht.streams.api.StreamOperationException;
import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.api.StreamOperationsProvider;
import name.ulbricht.streams.api.Terminal;

public final class StreamOperations {

	public static List<Class<?>> findSourceOperations() {
		final var operations = new ArrayList<Class<?>>();
		ServiceLoader.load(StreamOperationsProvider.class).forEach(l -> operations.addAll(l.getSourceOperations()));
		operations.sort(Comparator.comparing(Class::getSimpleName));
		return operations;
	}

	public static List<Class<?>> findIntermediateOperations() {
		final var operations = new ArrayList<Class<?>>();
		ServiceLoader.load(StreamOperationsProvider.class)
				.forEach(l -> operations.addAll(l.getIntermediateOperations()));
		operations.sort(Comparator.comparing(Class::getSimpleName));
		return operations;
	}

	public static List<Class<?>> findTerminalOperations() {
		final var operations = new ArrayList<Class<?>>();
		ServiceLoader.load(StreamOperationsProvider.class).forEach(l -> operations.addAll(l.getTerminalOperations()));
		operations.sort(Comparator.comparing(Class::getSimpleName));
		return operations;
	}

	public static Map<String, Supplier<StreamOperationSet>> findPresets() {
		final var presets = new HashMap<String, Supplier<StreamOperationSet>>();
		ServiceLoader.load(StreamOperationsProvider.class).forEach(l -> presets.putAll(l.getPresets()));
		return presets;
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
					.getConstructor((Class[]) null).newInstance((Object[]) null);
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
