package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;
import name.ulbricht.streams.api.StreamSource;

@Name("Text File Reader")
@Output(String.class)
@Configuration(TextFileReaderConfigurationPane.class)
public final class TextFileReader implements StreamSource<String> {

	private Path file = Paths.get(System.getProperty("user.dir"), "input.txt");

	Path getFile() {
		return this.file;
	}

	void setFile(final Path file) {
		this.file = file;
	}

	@Override
	public String getSourceCode() {
		return String.format("Files.lines(Paths.get(\"%s\"), StandardCharsets.UTF_8)",
				quote(this.file.toString()));
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
