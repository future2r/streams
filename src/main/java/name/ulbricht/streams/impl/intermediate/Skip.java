package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.IntermediateOperation;

@Configuration(name = "skip", type = ConfigurationType.INTEGER, displayName = "Skip Elements")
public final class Skip implements IntermediateOperation<Object, Object> {

	private int skip = 5;

	public int getSkip() {
		return this.skip;
	}

	public void setSkip(final int skip) {
		this.skip = skip;
	}

	@Override
	public String getSourceCode() {
		return String.format(".skip(%s)", Integer.toString(this.skip));
	}

	@Override
	public String getConfigurationText() {
		return String.format("skip=%s", Integer.toString(this.skip));
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.skip(this.skip);
	}
}