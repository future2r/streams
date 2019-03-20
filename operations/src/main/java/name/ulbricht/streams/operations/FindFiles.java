package name.ulbricht.streams.operations;

import static name.ulbricht.streams.operations.StringUtils.quote;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.EditorHint;
import name.ulbricht.streams.api.EditorType;
import name.ulbricht.streams.api.Source;

@JavaBean(description = "Finds all files in a directory and its subdirectories.")
@Source
public final class FindFiles implements Supplier<Stream<Path>> {

	private Path directory = Paths.get(System.getProperty("user.dir"));

	@BeanProperty(description = "Root directory for searching the files")
	@EditorHint(EditorType.DIRECTORY)
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
