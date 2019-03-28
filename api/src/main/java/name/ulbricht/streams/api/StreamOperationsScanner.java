package name.ulbricht.streams.api;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class StreamOperationsScanner {

	public static Stream<Class<?>> findSourceOperations(final String moduleName) {
		return findOperations(moduleName, Source.class).stream();
	}

	public static Stream<Class<?>> findIntermediateOperations(final String moduleName) {
		return findOperations(moduleName, Intermediate.class).stream();
	}

	public static Stream<Class<?>> findTerminalOperations(final String moduleName) {
		return findOperations(moduleName, Terminal.class).stream();
	}

	private static List<Class<?>> findOperations(final String moduleName,
			final Class<? extends Annotation> annotationClass) {
		final var module = ModuleLayer.boot().configuration().modules().stream()
				.filter(m -> m.name().equals(moduleName)).findFirst().get();

		try (final var moduleReader = module.reference().open()) {
			return moduleReader.list() //
					.filter(s -> s.endsWith(".class")) //
					.map(s -> s.substring(0, s.length() - 6).replace('/', '.')) //
					.map(StreamOperationsScanner::loadClass) //
					.filter(Optional::isPresent) //
					.map(Optional::get) //
					.filter(c -> isCompatible(c, annotationClass)) //
					.collect(Collectors.toList());
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
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			return Optional.empty();
		}
	}

	private StreamOperationsScanner() {
		// hidden
	}
}
