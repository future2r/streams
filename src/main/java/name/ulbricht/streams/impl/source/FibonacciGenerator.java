package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.beans.JavaBean;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@JavaBean(description = "Generates an infinite Fibonacci number sequence.")
@StreamOperation(type = SOURCE, output = Long.class)
public final class FibonacciGenerator implements Supplier<Stream<Long>> {

	@Override
	public Stream<Long> get() {
		return Stream.generate(new Supplier<Long>() {
			private long predecessor = 0, current = 1;

			@Override
			public Long get() {
				final var next = this.predecessor + this.current;
				this.predecessor = this.current;
				return this.current = next;
			}
		});
	}

	@Override
	public String toString() {
		return "Stream.generate(new Supplier<Long>() {\n" + //
				"  long predecessor = 0, current = 1;\n\n" + //
				"  public Long get() {\n" + //
				"    int next = predecessor + current;\n" + //
				"    predecessor = current;\n" + //
				"    return current = next;\n" + //
				"  }\n" + //
				"})";
	}
}
