package name.ulbricht.streams.impl.terminal;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Operation;
import name.ulbricht.streams.api.TerminalOperation;

@Operation(name = "List Collector")
public final class ListCollector implements TerminalOperation<Object> {

	@Override
	public String getSourceCode() {
		return ".collect(Collectors.toList())";
	}

	@Override
	public Object terminateStream(final Stream<Object> stream) {
		return stream.collect(Collectors.toList());
	}
}
