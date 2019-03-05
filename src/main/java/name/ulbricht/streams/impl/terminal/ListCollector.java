package name.ulbricht.streams.impl.terminal;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.TerminalOperation;

@Name("List Collector")
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
