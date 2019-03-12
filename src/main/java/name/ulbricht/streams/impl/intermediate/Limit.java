package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;

@Configuration(name = "limit", type = ConfigurationType.LONG, displayName = "Maximum Size")
public final class Limit implements IntermediateOperation<Object, Object> {

	private long limit = 5;

	public long getLimit() {
		return this.limit;
	}

	public void setLimit(final long limit) {
		this.limit = limit;
	}

	@Override
	public String getSourceCode() {
		return String.format(".limit(%s)", Long.toString(this.limit));
	}

	@Override
	public String getConfigurationText() {
		return String.format("limit=%s", Long.toString(this.limit));
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.limit(this.limit);
	}
}