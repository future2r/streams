package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.ConfigurationType.BOOLEAN;
import static name.ulbricht.streams.api.ConfigurationType.INTEGER;
import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Generates a sequence of integer values as a new stream.")
@StreamOperation(type = SOURCE, output = Integer.class)
public final class IntegerRange implements Supplier<Stream<Integer>> {

	private int start = 0;
	private int end = 100;
	private boolean closed = false;

	@Configuration(type = INTEGER, displayName = "Start", ordinal = 1)
	public int getStart() {
		return this.start;
	}

	public void setStart(final int start) {
		this.start = start;
	}

	@Configuration(type = INTEGER, displayName = "End", ordinal = 2)
	public int getEnd() {
		return this.end;
	}

	public void setEnd(final int end) {
		this.end = end;
	}

	@Configuration(type = BOOLEAN, displayName = "Range is closed", ordinal = 3)
	public boolean isClosed() {
		return this.closed;
	}

	public void setClosed(final boolean closed) {
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
