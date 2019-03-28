package name.ulbricht.streams.api.basic;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Generates a sequence of long values as a new stream.")
@Source
public final class LongRange implements Supplier<Stream<Long>> {

	private long start;
	private long end;
	private boolean closed;

	public LongRange() {
		this(0, 100, false);
	}

	public LongRange(final long start, final long end, final boolean closed) {
		this.start = start;
		this.end = end;
		this.closed = closed;
	}

	@BeanProperty(description = "Lower limit of the range")
	public long getStart() {
		return this.start;
	}

	public void setStart(final long start) {
		this.start = start;
	}

	@BeanProperty(description = "Upper limit of the range")
	public long getEnd() {
		return this.end;
	}

	public void setEnd(final long end) {
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
	public Stream<Long> get() {
		final LongStream stream;
		if (this.closed)
			stream = LongStream.rangeClosed(this.start, this.end);
		else
			stream = LongStream.range(this.start, this.end);
		return stream.boxed();
	}

	@Override
	public String toString() {
		return String.format("LongStream.range%s(%d, %d).boxed()", this.closed ? "Closed" : "", this.start, this.end);
	}
}
