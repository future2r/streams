package name.ulbricht.streams.impl.source;

import java.util.Random;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;
import name.ulbricht.streams.api.StreamSource;

@Name("Random Double Generator")
@Output(Double.class)
@Configuration(RandomDoubleGeneratorConfigurationPane.class)
public final class RandomDoubleGenerator implements StreamSource<Double> {

	private int number = 10;
	private double origin = 0.1;
	private double bound = 0.5;

	int getNumber() {
		return this.number;
	}

	void setNumber(final int number) {
		this.number = number;
	}

	double getOrigin() {
		return this.origin;
	}

	void setOrigin(final double origin) {
		this.origin = origin;
	}

	double getBound() {
		return this.bound;
	}

	void setBound(final double bound) {
		this.bound = bound;
	}

	@Override
	public String getSourceCode() {
		return String.format("new Random().doubles(%s, %s, %s).boxed()", Integer.toString(this.number),
				Double.toString(this.origin), Double.toString(this.bound));
	}

	@Override
	public String getConfigurationText() {
		return String.format("number=%s, origin=%s, bound=%s", Integer.toString(this.number),
				Double.toString(this.origin), Double.toString(this.bound));
	}

	@Override
	public Stream<Double> createStream() {
		return new Random().doubles(this.number, this.origin, this.bound).boxed();
	}
}
