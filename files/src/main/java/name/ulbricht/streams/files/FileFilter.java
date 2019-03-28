package name.ulbricht.streams.files;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Filters file by thier file name.")
@Intermediate
public final class FileFilter implements Function<Stream<Path>, Stream<Path>> {

	public enum Syntax {
		GLOB, REGEX;
	}

	private Syntax syntax;
	private String pattern;

	public FileFilter() {
		this(Syntax.GLOB, "**/*");
	}

	public FileFilter(final Syntax syntax, final String pattern) {
		this.syntax = Objects.requireNonNull(syntax, "syntax must not be null");
		this.pattern = Objects.requireNonNull(pattern, "pattern must not be null");
	}

	@BeanProperty(description = "Pattern to match the file name")
	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = Objects.requireNonNull(pattern, "pattern must not be null");
	}

	public Syntax getSyntax() {
		return this.syntax;
	}

	public void setSyntax(final Syntax syntax) {
		this.syntax = Objects.requireNonNull(syntax, "syntax must not be null");
		;
	}

	@Override
	public Stream<Path> apply(final Stream<Path> stream) {
		return stream.filter(f -> FileSystems.getDefault().getPathMatcher("glob:" + this.pattern).matches(f));
	}

	@Override
	public String toString() {
		return String.format(".filter(f -> FileSystems.getDefault().getPathMatcher(\"%s:%s\").matches(f))",
				this.syntax.name().toLowerCase(), quote(this.pattern));
	}

	private static String quote(final String s) {
		return s.replace("\\", "\\\\");
	}
}