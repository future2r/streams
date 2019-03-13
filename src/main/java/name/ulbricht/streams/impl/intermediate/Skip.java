package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Skip Elements", type = INTERMEDIATE)
@Configuration(name = "skip", type = ConfigurationType.LONG, displayName = "Skip Elements")
public final class Skip<T> implements Function<Stream<T>, Stream<T>> {

	private long skip = 5;

	public long getSkip() {
		return this.skip;
	}

	public void setSkip(final long skip) {
		this.skip = skip;
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.skip(this.skip);
	}

	@Override
	public String toString() {
		return String.format(".skip(%s)", Long.toString(this.skip));
	}
}