package name.ulbricht.streams.operations;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Generates a random sequence of double values as a new stream.")
@Source
public final class RandomDoubleGenerator implements Supplier<Stream<Double>> {

	private long number = 10;
	private double origin = 0.1;
	private double bound = 0.5;

	public RandomDoubleGenerator() {
		this(10);
	}

	public RandomDoubleGenerator(final long number) {
		this(number, Double.MIN_VALUE, Double.MAX_VALUE);
	}

	public RandomDoubleGenerator(final long number, final double origin, final double bound) {
		this.number = number;
		this.origin = origin;
		this.bound = bound;
	}

	@BeanProperty(description = "Number of values to generate")
	public long getNumber() {
		return this.number;
	}

	public void setNumber(final long number) {
		this.number = number;
	}

	@BeanProperty(description = "Minimum value (inclusive)")
	public double getOrigin() {
		return this.origin;
	}

	public void setOrigin(final double origin) {
		this.origin = origin;
	}

	@BeanProperty(description = "Maximum value (exclusive)")
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
