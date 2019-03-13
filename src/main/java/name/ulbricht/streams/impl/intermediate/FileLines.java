package name.ulbricht.streams.impl.intermediate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;

@Operation(name = "Read Text File", input = Path.class, output = String.class)
@Configuration(name = "encoding", type = ConfigurationType.ENCODING, displayName = "Encoding")
public final class FileLines implements IntermediateOperation<Path, String> {

	private Charset encoding = StandardCharsets.UTF_8;

	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(final Charset encoding) {
		this.encoding = encoding;
	}

	@Override
	public String getSourceCode() {
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
}