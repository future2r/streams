package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperation.quote;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;
import name.ulbricht.streams.api.StreamSource;

@Name("Find Files")
@Output(Path.class)
@Configuration(FindFilesConfigurationPane.class)
public final class FindFiles implements StreamSource<Path> {

	private Path directory = Paths.get(System.getProperty("user.dir"));

	Path getDirectory() {
		return this.directory;
	}

	void setDirectory(final Path directory) {
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
