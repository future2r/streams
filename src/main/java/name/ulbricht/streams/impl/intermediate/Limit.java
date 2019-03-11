package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;

@Configuration(name = "limit", type = ConfigurationType.INTEGER, displayName = "Maximum Size")
public final class Limit implements IntermediateOperation<Object, Object> {

	private int limit = 5;

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(final int limit) {
		this.limit = limit;
	}

	@Override
	public String getSourceCode() {
		return String.format(".limit(%s)", Integer.toString(this.limit));
	}

	@Override
	public String getConfigurationText() {
		return String.format("limit=%s", Integer.toString(this.limit));
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.limit(this.limit);
	}
}