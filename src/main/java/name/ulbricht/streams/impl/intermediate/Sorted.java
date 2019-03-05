package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Output;

@Input(Comparable.class)
@Output(Comparable.class)
public final class Sorted implements IntermediateOperation<Comparable<? super Comparable<?>>, Comparable<? super Comparable<?>>> {

	@Override
	public String getSourceCode() {
		return ".sorted()";
	}

	@Override
	public Stream<Comparable<? super Comparable<?>>> processStream(final Stream<Comparable<? super Comparable<?>>> stream) {
		return stream.sorted();
	}
}