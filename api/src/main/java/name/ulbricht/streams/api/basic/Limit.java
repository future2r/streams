package name.ulbricht.streams.api.basic;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Returns a stream consisting of the elements of this stream, truncated to be no longer than maximum size in length.")
@Intermediate
public final class Limit<T> implements Function<Stream<T>, Stream<T>> {

	private long limit;

	public Limit() {
		this(5);
	}

	public Limit(final long limit) {
		this.limit = limit;
	}

	@BeanProperty(description = "Maximum number of elements")
	public long getLimit() {
		return this.limit;
	}

	public void setLimit(final long limit) {
		this.limit = limit;
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.limit(this.limit);
	}

	@Override
	public String toString() {
		return String.format(".limit(%s)", Long.toString(this.limit));
	}
}