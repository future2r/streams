package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;
import static name.ulbricht.streams.impl.StringUtils.quote;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Find Files", type = SOURCE, output = Path.class, description = "Finds all files in a directory and its subdirectories.")
public final class FindFiles implements Supplier<Stream<Path>> {

	private Path directory = Paths.get(System.getProperty("user.dir"));

	@Configuration(type = ConfigurationType.DIRECTORY, displayName = "Directory")
	public Path getDirectory() {
		return this.directory;
	}

	public void setDirectory(final Path directory) {
		this.directory = directory;
	}

	@Override
	public Stream<Path> get() {
		try {
			return Files.find(this.directory, Integer.MAX_VALUE, (p, a) -> a.isRegularFile());
		} catch (final IOException ex) {
			ex.printStackTrace();
			return Stream.empty();
		}
	}

	@Override
	public String toString() {
		return String.format("Files.find(Paths.get(\"%s\"), Integer.MAX_VALUE, (p, a) -> a.isRegularFile())",
				quote(this.directory.toString()));
	}
}
