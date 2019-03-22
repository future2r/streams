package name.ulbricht.streams.operations;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Generates a sequence of integer values as a new stream.")
@Source
public final class IntegerRange implements Supplier<Stream<Integer>> {

	private int start;
	private int end;
	private boolean closed;

	public IntegerRange() {
		this(0, 100, false);
	}

	public IntegerRange(final int start, final int end, final boolean closed) {
		this.start = start;
		this.end = end;
		this.closed = closed;
	}

	@BeanProperty(description = "Lower limit of the range")
	public int getStart() {
		return this.start;
	}

	public void setStart(final int start) {
		this.start = start;
	}

	@BeanProperty(description = "Upper limit of the range")
	public int getEnd() {
		return this.end;
	}

	public void setEnd(final int end) {
		this.end = end;
	}

	@BeanProperty(description = "A closed range includes the upper limit")
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
