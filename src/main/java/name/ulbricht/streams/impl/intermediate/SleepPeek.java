package name.ulbricht.streams.impl.intermediate;

import static name.ulbricht.streams.api.ConfigurationType.LONG;
import static name.ulbricht.streams.api.StreamOperationType.INTERMEDIATE;

import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "Sleep Peek", type = INTERMEDIATE, description = "Sleeps for some time before forwarding the next element. This can be used for debugging purposes.")
public final class SleepPeek<T> implements Function<Stream<T>, Stream<T>> {

	private long millis = 100;

	@Configuration(displayName = "Milliseconds", type = LONG)
	public long getMillis() {
		return this.millis;
	}

	public void setMillis(long millis) {
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
