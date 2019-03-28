package name.ulbricht.streams.files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import name.ulbricht.streams.files.FileReader;

public final class FileReaderTest {

	@Test
	public void testExecution() {
		final var operation = new FileReader(Paths.get(System.getProperty("user.dir"), "testfiles", "text.txt"),
				StandardCharsets.UTF_8);

		final var result = operation.get().toArray(String[]::new);

		assertArrayEquals(new String[] { "Hello", "World" }, result);
	}

}
