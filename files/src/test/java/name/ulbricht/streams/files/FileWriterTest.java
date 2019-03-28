package name.ulbricht.streams.files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import name.ulbricht.streams.files.FileWriter;

public final class FileWriterTest {

	@Test
	public void testExecution() throws IOException {
		Path tempFile = Files.createTempFile("test", ".txt");

		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new FileWriter(tempFile, StandardCharsets.UTF_8);

		operation.apply(stream);

		final var lines = Files.lines(tempFile).toArray(String[]::new);

		assertArrayEquals(new String[] {"This", "is", "a", "test"}, lines);
	}

}
