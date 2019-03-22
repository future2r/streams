package name.ulbricht.streams.api.basic;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns a stream consisting of the remaining elements of this stream after discarding the first elements of the stream."
		+ " If this stream contains fewer than n elements then an empty stream will be returned.")
@Intermediate
public final class Skip<T> implements Function<Stream<T>, Stream<T>> {

	private long skip;

	public Skip() {
		this(5);
	}

	public Skip(final long skip) {
		this.skip = skip;
	}

	@BeanProperty(description = "Number of elements to skip")
	public long getSkip() {
		return this.skip;
	}

	public void setSkip(final long skip) {
		this.skip = skip;
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.skip(this.skip);
	}

	@Override
	public String toString() {
		return String.format(".skip(%s)", Long.toString(this.skip));
	}
}