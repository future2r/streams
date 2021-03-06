package name.ulbricht.streams.files;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Finds all files in a directory and its subdirectories.")
@Source
public final class FileFinder implements Supplier<Stream<Path>> {

	private Path directory;

	public FileFinder() {
		this(Paths.get(System.getProperty("user.dir")));
	}

	public FileFinder(final Path directory) {
		this.directory = Objects.requireNonNull(directory, "directory must not be null");
	}

	@BeanProperty(description = "Root directory for searching the files")
	public Path getDirectory() {
		return this.directory;
	}

	public void setDirectory(final Path directory) {
		this.directory = Objects.requireNonNull(directory, "directory must not be null");
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

	private static String quote(final String s) {
		return s.replace("\\", "\\\\");
	}
}
