package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.SourceOperation;

@Operation(name = "Text File Reader", output = String.class)
@Configuration(name = "file", type = ConfigurationType.FILE, displayName = "Text File")
@Configuration(name = "encoding", type = ConfigurationType.ENCODING, displayName = "Encoding")
public final class TextFileReader implements SourceOperation<String> {

	private Path file = Paths.get(System.getProperty("user.dir"), "input.txt");
	private Charset encoding = StandardCharsets.UTF_8;

	public Path getFile() {
		return this.file;
	}

	public void setFile(final Path file) {
		this.file = file;
	}

	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(final Charset encoding) {
		this.encoding = encoding;
	}

	@Override
	public String getSourceCode() {
		return String.format("Files.lines(Paths.get(\"%s\"), Charset.forName(\"%s\"))", quote(this.file.toString()),
				this.encoding.name());
	}

	@Override
	public String getConfigurationText() {
		return this.file.toString();
	}

	@Override
	public Stream<String> createStream() {
		try {
			return Files.lines(this.file, this.encoding);
		} catch (final IOException ex) {
			return Stream.of("could not read file");
		}
	}
}
