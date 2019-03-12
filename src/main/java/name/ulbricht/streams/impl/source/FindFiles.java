package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.impl.StringUtils.quote;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.SourceOperation;

@Operation(name = "Find Files", output = Path.class)
@Configuration(name = "directory", type = ConfigurationType.DIRECTORY, displayName = "Directory")
public final class FindFiles implements SourceOperation<Path> {

	private Path directory = Paths.get(System.getProperty("user.dir"));

	public Path getDirectory() {
		return this.directory;
	}

	public void setDirectory(final Path directory) {
		this.directory = directory;
	}

	@Override
	public String getSourceCode() {
		return String.format("Files.find(Paths.get(\"%s\"), Integer.MAX_VALUE, (p, a) -> a.isRegularFile())",
				quote(this.directory.toString()));
	}

	@Override
	public String getConfigurationText() {
		return this.directory.toString();
	}

	@Override
	public Stream<Path> createStream() {
		try {
			return Files.find(this.directory, Integer.MAX_VALUE, (p, a) -> a.isRegularFile());
		} catch (final IOException ex) {
			ex.printStackTrace();
			return Stream.empty();
		}
	}
}
