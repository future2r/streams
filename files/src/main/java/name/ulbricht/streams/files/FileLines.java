package name.ulbricht.streams.files;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Reads a text file and provides a stream with all lines.")
@Intermediate
public final class FileLines implements Function<Stream<Path>, Stream<String>> {

	private Charset encoding = StandardCharsets.UTF_8;

	@BeanProperty(description = "Character set for reading the file")
	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(final Charset encoding) {
		this.encoding = encoding;
	}

	@Override
	public Stream<String> apply(final Stream<Path> stream) {
		return stream.flatMap(f -> {
			try {
				return Files.lines(f, this.encoding);
			} catch (IOException ex) {
				ex.printStackTrace();
				return Stream.empty();
			}
		});
	}

	@Override
	public String toString() {
		return String.format("stream.flatMap(f -> {\n" //
				+ "  try {\n" //
				+ "    return Files.lines(f, Charset.forName(\"%s\"));\n" //
				+ "  } catch (IOException ex) {\n" //
				+ "    ex.printStackTrace();\n" //
				+ "    return Stream.empty();\n" //
				+ "  }\n"//
				+ "})", //
				this.encoding.name());
	}
}