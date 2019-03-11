package name.ulbricht.streams.impl.intermediate;

import java.util.stream.Stream;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Operation;

@Operation(name = "System.out Peek")
public final class SystemOutPeek implements IntermediateOperation<Object, Object> {

	@Override
	public String getSourceCode() {
		return ".peek(System.out::println)";
	}
	
	@Override
	public Stream<Object> processStream(final Stream<Object> stream) {
		return stream.peek(System.out::println);
	}
}
