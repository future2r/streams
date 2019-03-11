package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.StreamSource;

@Operation(name = "Text File Reader", output = String.class)
@Configuration(name = "file", type = ConfigurationType.FILE, displayName = "Text File")
public final class TextFileReader implements StreamSource<String> {

	private Path file = Paths.get(System.getProperty("user.dir"), "input.txt");

	public Path getFile() {
		return this.file;
	}

	public void setFile(final Path file) {
		this.file = file;
	}

	@Override
	public String getSourceCode() {
		return String.format("Files.lines(Paths.get(\"%s\"), StandardCharsets.UTF_8)", quote(this.file.toString()));
	}

	@Override
	public String getConfigurationText() {
		return this.file.toString();
	}

	@Override
	public Stream<String> createStream() {
		try {
			return Files.lines(this.file, StandardCharsets.UTF_8);
		} catch (final IOException ex) {
			return Stream.of("could not read file");
		}
	}
}
