package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Output;

@Input(String.class)
@Output(String.class)
public final class UpperCase implements IntermediateOperation<String, String> {

	@Override
	public String getSourceCode() {
		return ".map(String::toUpperCase)";
	}

	@Override
	public Stream<String> processStream(final Stream<String> stream) {
		return stream.map(String::toUpperCase);
	}
}
