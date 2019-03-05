package name.ulbricht.streams.impl.intermediate;

import java.util.Comparator;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;

@Name("Sorted Reverse")
@Input(Comparable.class)
@Output(Comparable.class)
public final class SortedReverse
		implements IntermediateOperation<Comparable<? super Comparable<?>>, Comparable<? super Comparable<?>>> {

	@Override
	public String getSourceCode() {
		return ".sorted(Comparator.reverseOrder())";
	}

	@Override
	public Stream<Comparable<? super Comparable<?>>> processStream(
			final Stream<Comparable<? super Comparable<?>>> stream) {
		return stream.sorted(Comparator.reverseOrder());
	}
}