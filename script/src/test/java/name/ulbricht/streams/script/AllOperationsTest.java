package name.ulbricht.streams.script;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import name.ulbricht.streams.operations.OperationsProvider;

public final class AllOperationsTest {

	@Test
	public void testProvider() {
		final var provider = new ScriptOperationsProvider();

		assertAll(() -> assertEquals(1, provider.getSourceOperations().size()),
				() -> assertEquals(6, provider.getIntermediateOperations().size()),
				() -> assertEquals(6, provider.getTerminalOperations().size()),
				() -> assertEquals(4, provider.getPresets().size()));
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
