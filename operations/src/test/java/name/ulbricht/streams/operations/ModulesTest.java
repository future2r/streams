package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class ModulesTest {

	@Test
	public void testExecution() {
		final var operation = new Modules();

		assertTrue(operation.get().count() > 0);
	}

}
