package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.IntermediateOperation;

@Configuration(SkipConfigurationPane.class)
public final class Skip implements IntermediateOperation<Object, Object> {

	private int skip = 5;

	int getSkip() {
		return this.skip;
	}

	void setSkip(final int skip) {
		this.skip = skip;
	}

	@Override
	public String getSourceCode() {
		return ".skip(" + this.skip + ")";
	}

	@Override
	public String getConfigurationText() {
		return "skip=" + this.skip;
	}

	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.skip(this.skip);
	}
}