package name.ulbricht.streams.impl.source;

import java.util.Random;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;
import name.ulbricht.streams.api.StreamSource;

@Name("Random Integer Generator")
@Output(Integer.class)
@Configuration(name = "number", type = ConfigurationType.INTEGER, displayName = "Number")
@Configuration(name = "origin", type = ConfigurationType.INTEGER, displayName = "Origin (inclusive)")
@Configuration(name = "bound", type = ConfigurationType.INTEGER, displayName = "Bound (exclusive)")
public final class RandomIntegerGenerator implements StreamSource<Integer> {

	private int number = 10;
	private int origin = 0;
	private int bound = 100;

	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
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
		return String.format("new Random().ints(%s, %s, %s).boxed()", Integer.toString(this.number),
				Integer.toString(this.origin), Integer.toString(this.bound));
	}

	@Override
	public String getConfigurationText() {
		return String.format("number=%s, origin=%s, bound=%s", Integer.toString(this.number),
				Integer.toString(this.origin), Integer.toString(this.bound));
	}

	@Override
	public Stream<Integer> createStream() {
		return new Random().ints(this.number, this.origin, this.bound).boxed();
	}
}
