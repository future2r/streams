package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Limit Elements", type = INTERMEDIATE)
@Configuration(name = "limit", type = ConfigurationType.LONG, displayName = "Maximum Size")
public final class Limit implements Function<Stream<Object>, Stream<Object>> {

	private long limit = 5;

	public long getLimit() {
		return this.limit;
	}

	public void setLimit(final long limit) {
		this.limit = limit;
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.limit(this.limit);
	}

	@Override
	public String toString() {
		return String.format(".limit(%s)", Long.toString(this.limit));
	}
}