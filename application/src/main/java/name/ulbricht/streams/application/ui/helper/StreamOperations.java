package name.ulbricht.streams.application.ui.helper;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.Intermediate;
import name.ulbricht.streams.api.Source;
import name.ulbricht.streams.api.Terminal;

public final class StreamOperations {

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

	public static Optional<EditorHint> getEditorHint(final PropertyDescriptor property) {
		return Optional.ofNullable(property.getReadMethod().getAnnotation(EditorHint.class));
	}

	public static Class<?>[] findSourceOperations() {
		return findOperations(Source.class);
	}

	public static Class<?>[] findIntermediateOperations() {
		return findOperations(Intermediate.class);
	}

	public static Class<?>[] findTerminalOperations() {
		return findOperations(Terminal.class);
	}

	private static Class<?>[] findOperations(final Class<? extends Annotation> annotationClass) {
		final var module = ModuleLayer.boot().configuration().modules().stream()
				.filter(m -> m.name().equals("name.ulbricht.streams.application")).findFirst().get();

		try (final var moduleReader = module.reference().open()) {
			return (Class<?>[]) moduleReader.list() //
					.filter(s -> s.startsWith("name/ulbricht/streams/application/operations/") && s.endsWith(".class")) //
					.map(s -> s.substring(0, s.length() - 6).replace('/', '.')) //
					.map(StreamOperations::loadClass) //
					.filter(Optional::isPresent) //
					.map(Optional::get) //
					.filter(c -> isCompatible(c, annotationClass)) //
					.sorted(Comparator.comparing(Class::getSimpleName)) //
					.toArray(Class<?>[]::new);
		} catch (final IOException ex) {
			throw new StreamOperationException(ex);
		}
	}

	private static boolean isCompatible(final Class<?> candidateClass,
			final Class<? extends Annotation> annotationClass) {
		final var annotation = candidateClass.getAnnotation(annotationClass);
		if (annotation != null) {
			if (annotationClass == Source.class)
				checkImplements(candidateClass, Supplier.class);
			else if (annotationClass == Intermediate.class || annotationClass == Terminal.class)
				checkImplements(candidateClass, Function.class);
			return true;
		}
		return false;
	}

	private static void checkImplements(final Class<?> candidateClass, final Class<?> requiredInterface) {
		if (!requiredInterface.isAssignableFrom(candidateClass))
			throw new StreamOperationException(String.format("Operation %s must implement %s",
					candidateClass.getSimpleName(), requiredInterface.getSimpleName()));
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
