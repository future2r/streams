package name.ulbricht.streams.operations;

import static name.ulbricht.streams.operations.StringUtils.quote;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Reads a text file and provides a stream with all lines.")
@Source
public final class TextFileReader implements Supplier<Stream<String>> {

	private Path file;
	private Charset encoding;

	public TextFileReader() {
		this(Paths.get(System.getProperty("user.dir"), "input.txt"));
	}

	public TextFileReader(final Path file) {
		this(file, Charset.defaultCharset());
	}

	public TextFileReader(final Path file, final Charset encoding) {
		this.file = Objects.requireNonNull(file, "file must not be null");
		this.encoding = Objects.requireNonNull(encoding, "encoding must not be null");
	}

	@BeanProperty(description = "Path of the file to read")
	public Path getFile() {
		return this.file;
	}

	public void setFile(final Path file) {
		this.file = Objects.requireNonNull(file, "file must not be null");
	}

	@BeanProperty(description = "Character set for reading the file")
	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(final Charset encoding) {
		this.encoding = Objects.requireNonNull(encoding, "encoding must not be null");
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
