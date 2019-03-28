package name.ulbricht.streams.extended;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public final class AllOperationsTest {

	@Test
	public void testProvider() {
		final var provider = new ExtendedOperationsProvider();

		assertAll(() -> assertEquals(10, provider.getSourceOperations().count()),
				() -> assertEquals(4, provider.getIntermediateOperations().count()),
				() -> assertEquals(1, provider.getTerminalOperations().count()),
				() -> assertEquals(1, provider.getPresets().size()));
	}

	@ParameterizedTest
	@MethodSource
	public void testConstructors(final Class<?> operationClass) throws ReflectiveOperationException {
		operationClass.getConstructor((Class<?>[]) null).newInstance((Object[]) null);
	}

	public static Stream<Class<?>> testConstructors() {
		final var provider = new ExtendedOperationsProvider();

		return Stream.concat(Stream.concat(provider.getSourceOperations(), provider.getIntermediateOperations()),
				provider.getTerminalOperations());
	}
}
