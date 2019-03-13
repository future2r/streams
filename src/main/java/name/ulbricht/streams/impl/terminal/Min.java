package name.ulbricht.streams.impl.terminal;

import java.util.Comparator;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;

@Operation(name = "Minimum", input = Comparable.class)
public final class Min<T extends Comparable<T>> implements TerminalOperation<T> {

	@Override
	public String getSourceCode() {
		return ".min(Comparator.naturalOrder())";
	}

	@Override
	public Object apply(final Stream<T> stream) {
		return stream.min(Comparator.naturalOrder());
	}
}
