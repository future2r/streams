package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.IntermediateOperation;

@Configuration(LimitConfigurationPane.class)
public final class Limit implements IntermediateOperation<Object, Object> {

	private int limit = 5;

	int getLimit() {
		return this.limit;
	}

	void setLimit(final int limit) {
		this.limit = limit;
	}

	@Override
	public String getSourceCode() {
		return ".limit(" + this.limit + ")";
	}

	@Override
	public String getConfigurationText() {
		return "limit=" + this.limit;
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.limit(this.limit);
	}
}