package name.ulbricht.streams.impl.intermediate;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Sleeps for some time before forwarding the next element. This can be used for debugging purposes.")
@Intermediate
public final class SleepPeek<T> implements Function<Stream<T>, Stream<T>> {

	private long millis = 100;

	@BeanProperty(description = "Milliseconds to pause the thread")
	public long getMillis() {
		return this.millis;
	}

	public void setMillis(final long millis) {
		this.millis = millis;
	}

	@Override
	public Stream<T> apply(final Stream<T> stream) {
		return stream.peek(e -> {
			try {
				Thread.sleep(this.millis);
			} catch (InterruptedException ex) {
			}
		});
	}

	@Override
	public String toString() {
		return ".peek(e -> { try {Thread.sleep(this.millis); } catch (InterruptedException ex) {} })";
	}
}
