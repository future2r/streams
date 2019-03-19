package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.ConfigurationType.ENCODING;
import static name.ulbricht.streams.api.ConfigurationType.FILE;
import static name.ulbricht.streams.api.StreamOperationType.SOURCE;
import static name.ulbricht.streams.impl.StringUtils.quote;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Text File Reader", type = SOURCE, output = String.class)
public final class TextFileReader implements Supplier<Stream<String>> {

	private Path file = Paths.get(System.getProperty("user.dir"), "input.txt");
	private Charset encoding = StandardCharsets.UTF_8;

	@Configuration(type = FILE, displayName = "Text File", ordinal = 1)
	public Path getFile() {
		return this.file;
	}

	public void setFile(final Path file) {
		this.file = file;
	}

	@Configuration(type = ENCODING, displayName = "Encoding", ordinal = 2)
	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(final Charset encoding) {
		this.encoding = encoding;
	}

	@Override
	public Stream<String> get() {
		try {
			return Files.lines(this.file, this.encoding);
		} catch (final IOException ex) {
			return Stream.of("could not read file");
		}
	}

	@Override
	public String toString() {
		return String.format("Files.lines(Paths.get(\"%s\"), Charset.forName(\"%s\"))", quote(this.file.toString()),
				this.encoding.name());
	}
}
