package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.ConfigurationType.LONG;
import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Limit Elements", type = INTERMEDIATE)
public final class Limit<T> implements Function<Stream<T>, Stream<T>> {

	private long limit = 5;

	@Configuration(type = LONG, displayName = "Maximum size")
	public long getLimit() {
		return this.limit;
	}

	public void setLimit(final long limit) {
		this.limit = limit;
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.limit(this.limit);
	}

	@Override
	public String toString() {
		return String.format(".limit(%s)", Long.toString(this.limit));
	}
}