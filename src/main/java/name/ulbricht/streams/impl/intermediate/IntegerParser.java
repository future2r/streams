package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;

@Name("Integer Parser")
@Input(String.class)
@Output(Integer.class)
public final class IntegerParser implements IntermediateOperation<String, Integer> {

	@Override
	public String getSourceCode() {
		return ".map(Integer::parseInt)";
	}

	@Override
	public Stream<Integer> processStream(final Stream<String> stream) {
		return stream.map(Integer::parseInt);
	}
}
