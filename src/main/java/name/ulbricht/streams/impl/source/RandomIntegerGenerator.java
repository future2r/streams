package name.ulbricht.streams.impl.source;

import java.util.Random;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;
import name.ulbricht.streams.api.StreamSource;

@Name("Random Integer Generator")
@Output(Integer.class)
@Configuration(RandomIntegerGeneratorConfigurationPane.class)
public final class RandomIntegerGenerator implements StreamSource<Integer> {

	private int number = 10;
	private int origin = 0;
	private int bound = 100;

	int getNumber() {
		return this.number;
	}

	void setNumber(final int number) {
		this.number = number;
	}

	int getOrigin() {
		return this.origin;
	}

	void setOrigin(final int origin) {
		this.origin = origin;
	}

	int getBound() {
		return this.bound;
	}

	void setBound(final int bound) {
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
