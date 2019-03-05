package name.ulbricht.streams.impl.terminal;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.TerminalOperation;

@Name("Text File Writer")
@Input(String.class)
@Configuration(TextFileWriterConfigurationPane.class)
public final class TextFileWriter implements TerminalOperation<String> {

	private Path file = Paths.get(System.getProperty("user.dir"), "output.txt");

	Path getFile() {
		return this.file;
	}

	void setFile(Path file) {
		this.file = file;
	}

	@Override
	public String getSourceCode() {
		return ".map(s -> s + '\\n').forEach(s -> {\n" //
				+ "  try {\n" //
				+ "    Files.writeString(\"" + quote(this.file.toString())
				+ "\", s, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);\n"
				+ "  } catch (IOException ex) {}\n" //
				+ "})";
	}

	@Override
	public String getConfigurationText() {
		return this.file.toString();
	}

	@Override
	public Object terminateStream(final Stream<String> stream) {
		stream.map(s -> s + '\n').forEach(s -> {
			try {
				Files.writeString(this.file, s, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
						StandardOpenOption.APPEND);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		return null;
	}
}
