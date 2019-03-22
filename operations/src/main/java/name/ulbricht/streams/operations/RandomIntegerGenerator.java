package name.ulbricht.streams.operations;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Generates a random sequence of integer values as a new stream.")
@Source
public final class RandomIntegerGenerator implements Supplier<Stream<Integer>> {

	private long number;
	private int origin;
	private int bound;

	public RandomIntegerGenerator() {
		this(10, 0, 100);
	}

	public RandomIntegerGenerator(final long number) {
		this(number, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public RandomIntegerGenerator(final long number, final int origin, final int bound) {
		this.number = number;
		this.origin = origin;
		this.bound = bound;
	}

	@BeanProperty(description = "Number of elements to generate")
	public long getNumber() {
		return this.number;
	}

	public void setNumber(final long number) {
		this.number = number;
	}

	@BeanProperty(description = "Minimum value (inclusive)")
	public int getOrigin() {
		return this.origin;
	}

	public void setOrigin(final int origin) {
		this.origin = origin;
	}

	@BeanProperty(description = "Maximum value (exclusive)")
	public int getBound() {
		return this.bound;
	}

	public void setBound(final int bound) {
		this.bound = bound;
	}

	@Override
	public Stream<Integer> get() {
		return new Random().ints(this.number, this.origin, this.bound).boxed();
	}

	@Override
	public String toString() {
		return String.format("new Random().ints(%s, %s, %s).boxed()", Long.toString(this.number),
				Integer.toString(this.origin), Integer.toString(this.bound));
	}
}
