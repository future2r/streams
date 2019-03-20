package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.impl.StringUtils.quote;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.Terminal;

@JavaBean(description = "Writes all strings the the stream as lines into a text file.")
@Terminal
public final class TextFileWriter implements Function<Stream<String>, Void> {

	private Path file = Paths.get(System.getProperty("user.dir"), "output.txt");
	private Charset encoding = StandardCharsets.UTF_8;

	@BeanProperty(description = "Path of the file to write")
	@EditorHint(EditorType.FILE)
	public Path getFile() {
		return this.file;
	}

	public void setFile(Path file) {
		this.file = file;
	}

	@BeanProperty(description = "Character set for writing the file")
	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(final Charset encoding) {
		this.encoding = encoding;
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
