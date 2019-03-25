package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public final class AllOperationsTest {

	@Test
	public void testProvider() {
		final var provider = new OperationsProvider();

		assertEquals(15, provider.getSourceOperations().size());
		assertEquals(11, provider.getIntermediateOperations().size());
		assertEquals(3, provider.getTerminalOperations().size());

		assertEquals(4, provider.getPresets().size());
	}

	@ParameterizedTest
	@MethodSource
	public void testConstructors(final Class<?> operationClass) throws ReflectiveOperationException {
		operationClass.getConstructor((Class<?>[]) null).newInstance((Object[]) null);
	}

	public static Stream<Class<?>> testConstructors() {
		final var provider = new OperationsProvider();

		return Stream.concat(
				Stream.concat(provider.getSourceOperations().stream(), provider.getIntermediateOperations().stream()),
				provider.getTerminalOperations().stream());
	}
}
