package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public final class FindFilesTest {

	@Test
	public void testExecution() {
		final var operation = new FindFiles(Paths.get(System.getProperty("user.dir"), "testfiles"));

		final var result = operation.get().toArray(Path[]::new);

		assertEquals(1, result.length);
		assertEquals("text.txt", result[0].getFileName().toString());
	}

}
