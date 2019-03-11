package name.ulbricht.streams.impl.intermediate;

import java.util.Objects;
import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;

@Name("ToString Mapper")
@Output(String.class)
public final class ToStringMapper implements IntermediateOperation<Object, String> {

	@Override
	public String getSourceCode() {
		return ".map(Objects::toString)";
	}

	@Override
	public Stream<String> processStream(final Stream<Object> stream) {
		return stream.map(Objects::toString);
	}
}