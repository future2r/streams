package name.ulbricht.streams.impl.source;

import java.util.Random;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.StreamSource;

@Operation(name = "Random Double Generator", output = Double.class)
@Configuration(name = "number", type = ConfigurationType.INTEGER, displayName = "Number")
@Configuration(name = "origin", type = ConfigurationType.DOUBLE, displayName = "Origin (inclusive)")
@Configuration(name = "bound", type = ConfigurationType.DOUBLE, displayName = "Bound (exclusive)")
public final class RandomDoubleGenerator implements StreamSource<Double> {

	private int number = 10;
	private double origin = 0.1;
	private double bound = 0.5;

	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public double getOrigin() {
		return this.origin;
	}

	public void setOrigin(final double origin) {
		this.origin = origin;
	}

	public double getBound() {
		return this.bound;
	}

	public void setBound(final double bound) {
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
