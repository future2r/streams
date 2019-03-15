package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Integer Range", type = SOURCE, output = Integer.class, description = "Generates a sequence of integer values as a new stream.")
public final class IntegerRange implements Supplier<Stream<Integer>> {

	private int start = 0;
	private int end = 100;
	private boolean closed = false;

	@Configuration(type = ConfigurationType.INTEGER, displayName = "Start", ordinal = 1)
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	@Configuration(type = ConfigurationType.INTEGER, displayName = "End", ordinal = 2)
	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Configuration(type = ConfigurationType.BOOLEAN, displayName = "Range is closed", ordinal = 3)
	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@Override
	public Stream<Integer> get() {
		final IntStream stream;
		if (this.closed)
			stream = IntStream.rangeClosed(this.start, this.end);
		else
			stream = IntStream.range(this.start, this.end);
		return stream.boxed();
	}

	@Override
	public String toString() {
		return String.format("IntStream.range%s(%s, %s).boxed()", this.closed ? "Closed" : "",
				Integer.toString(this.start), Integer.toString(this.end));
	}
}
