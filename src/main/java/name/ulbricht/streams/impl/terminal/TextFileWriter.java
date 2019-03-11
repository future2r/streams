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
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;

@Operation(name = "Text File Writer", input = String.class)
@Configuration(name = "file", type = ConfigurationType.FILE, displayName = "Current File")
public final class TextFileWriter implements TerminalOperation<String> {

	private Path file = Paths.get(System.getProperty("user.dir"), "output.txt");

	public Path getFile() {
		return this.file;
	}

	public void setFile(Path file) {
		this.file = file;
	}

	@Override
	public String getSourceCode() {
		return ".map(s -> s + '\\n').forEach(s -> {\n" //
				+ "  try {\n" //
				+ "    Files.writeString(Paths.get(\"" + quote(this.file.toString())
				+ "\"), s, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);\n"
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
