package name.ulbricht.streams.impl.source;

import java.util.Random;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.SourceOperation;

@Operation(name = "Random Integer Generator", output = Integer.class)
@Configuration(name = "number", type = ConfigurationType.LONG, displayName = "Number")
@Configuration(name = "origin", type = ConfigurationType.INTEGER, displayName = "Origin (inclusive)")
@Configuration(name = "bound", type = ConfigurationType.INTEGER, displayName = "Bound (exclusive)")
public final class RandomIntegerGenerator implements SourceOperation<Integer> {

	private long number = 10;
	private int origin = 0;
	private int bound = 100;

	public long getNumber() {
		return this.number;
	}

	public void setNumber(final long number) {
		this.number = number;
	}

	public int getOrigin() {
		return this.origin;
	}

	public void setOrigin(final int origin) {
		this.origin = origin;
	}

	public int getBound() {
		return this.bound;
	}

	public void setBound(final int bound) {
		this.bound = bound;
	}

	@Override
	public String getSourceCode() {
		return String.format("new Random().ints(%s, %s, %s).boxed()", Long.toString(this.number),
				Integer.toString(this.origin), Integer.toString(this.bound));
	}

	@Override
	public String getConfigurationText() {
		return String.format("number=%s, origin=%s, bound=%s", Long.toString(this.number),
				Integer.toString(this.origin), Integer.toString(this.bound));
	}

	@Override
	public Stream<Integer> createStream() {
		return new Random().ints(this.number, this.origin, this.bound).boxed();
	}
}
