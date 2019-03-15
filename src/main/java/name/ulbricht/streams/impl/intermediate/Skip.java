package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Skip Elements", type = INTERMEDIATE, description = "Returns a stream consisting of the remaining elements of this stream after discarding the first elements of the stream. If this stream contains fewer than n elements then an empty stream will be returned.")
public final class Skip<T> implements Function<Stream<T>, Stream<T>> {

	private long skip = 5;

	@Configuration(type = ConfigurationType.LONG, displayName = "Skip Elements")
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