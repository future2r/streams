package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Random Integer Generator", type = SOURCE, output = Integer.class)
@Configuration(name = "number", type = ConfigurationType.LONG, displayName = "Number")
@Configuration(name = "origin", type = ConfigurationType.INTEGER, displayName = "Origin (inclusive)")
@Configuration(name = "bound", type = ConfigurationType.INTEGER, displayName = "Bound (exclusive)")
public final class RandomIntegerGenerator implements Supplier<Stream<Integer>> {

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
	public Stream<Integer> get() {
		return new Random().ints(this.number, this.origin, this.bound).boxed();
	}

	@Override
	public String toString() {
		return String.format("new Random().ints(%s, %s, %s).boxed()", Long.toString(this.number),
				Integer.toString(this.origin), Integer.toString(this.bound));
	}
}
