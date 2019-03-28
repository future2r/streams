package name.ulbricht.streams.extended;

import static name.ulbricht.streams.extended.StringUtils.quote;
import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Writes all strings the the stream as lines into a text file.")
@Terminal
public final class TextFileWriter implements Function<Stream<String>, Void> {

	private Path file;
	private Charset encoding;

	public TextFileWriter() {
		this(Paths.get(System.getProperty("user.dir"), "output.txt"));
	}

	public TextFileWriter(final Path file) {
		this(file, Charset.defaultCharset());
	}

	public TextFileWriter(final Path file, final Charset encoding) {
		this.file = Objects.requireNonNull(file, "file must not be null");
		this.encoding = Objects.requireNonNull(encoding, "encoding must not be null");
	}

	@BeanProperty(description = "Path of the file to write")
	public Path getFile() {
		return this.file;
	}

	public void setFile(Path file) {
		this.file = Objects.requireNonNull(file, "file must not be null");
	}

	@BeanProperty(description = "Character set for writing the file")
	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(final Charset encoding) {
		this.encoding = Objects.requireNonNull(encoding, "encoding must not be null");
	}

	@Override
	public Void apply(final Stream<String> stream) {
		stream.map(s -> s + '\n').forEach(s -> {
			try {
				Files.writeString(this.file, s, this.encoding, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		return null;
	}

	@Override
	public String toString() {
		return String.format(".map(s -> s + '\\n').forEach(s -> {\n" //
				+ "  try {\n" //
				+ "    Files.writeString(Paths.get(\"" + quote(this.file.toString())
				+ "\"), s, Charset.forName(\"%s\"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);\n"
				+ "  } catch (IOException ex) {}\n" //
				+ "})", //
				this.encoding.name());
	}
}
