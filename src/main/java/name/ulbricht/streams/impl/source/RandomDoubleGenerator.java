package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Random Double Generator", type = SOURCE, output = Double.class)
@Configuration(name = "number", type = ConfigurationType.LONG, displayName = "Number")
@Configuration(name = "origin", type = ConfigurationType.DOUBLE, displayName = "Origin (inclusive)")
@Configuration(name = "bound", type = ConfigurationType.DOUBLE, displayName = "Bound (exclusive)")
public final class RandomDoubleGenerator implements Supplier<Stream<Double>> {

	private long number = 10;
	private double origin = 0.1;
	private double bound = 0.5;

	public long getNumber() {
		return this.number;
	}

	public void setNumber(final long number) {
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
	public Stream<Double> get() {
		return new Random().doubles(this.number, this.origin, this.bound).boxed();
	}

	@Override
	public String toString() {
		return String.format("new Random().doubles(%s, %s, %s).boxed()", Long.toString(this.number),
				Double.toString(this.origin), Double.toString(this.bound));
	}
}
