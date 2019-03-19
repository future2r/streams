package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.ConfigurationType.INTEGER;
import static name.ulbricht.streams.api.ConfigurationType.LONG;
import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.beans.JavaBean;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Generates a random sequence of integer values as a new stream.")
@StreamOperation(type = SOURCE, output = Integer.class)
public final class RandomIntegerGenerator implements Supplier<Stream<Integer>> {

	private long number = 10;
	private int origin = 0;
	private int bound = 100;

	@Configuration(type = LONG, displayName = "Number", ordinal = 1)
	public long getNumber() {
		return this.number;
	}

	public void setNumber(final long number) {
		this.number = number;
	}

	@Configuration(type = INTEGER, displayName = "Origin (inclusive)", ordinal = 2)
	public int getOrigin() {
		return this.origin;
	}

	public void setOrigin(final int origin) {
		this.origin = origin;
	}

	@Configuration(type = INTEGER, displayName = "Bound (exclusive)", ordinal = 3)
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
