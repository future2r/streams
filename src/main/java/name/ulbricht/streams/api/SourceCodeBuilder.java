package name.ulbricht.streams.api;

import java.util.List;
import java.util.stream.Collectors;

public final class SourceCodeBuilder extends StreamHandler {

	public SourceCodeBuilder(final StreamSource<?> streamSource,
			final List<IntermediateOperation<?, ?>> intermediatOperations,
			final TerminalOperation<?> terminalOperation) {
		super(streamSource, intermediatOperations, terminalOperation);
	}

	public String getSourceCode() {
		final var sourceCode = new StringBuilder();

		sourceCode.append(String.format("// %s%n", StreamOperation.getDisplayName(this.streamSource)));
		sourceCode.append(this.streamSource.getSourceCode().lines().collect(Collectors.joining("\n", "", "\n")));

		sourceCode.append(this.intermediatOperations.stream()
				.map(op -> String.format("%n// %s%n%s%n", StreamOperation.getDisplayName(op), op.getSourceCode()))
				.flatMap(String::lines).collect(Collectors.joining("\n", "", "\n")));

		sourceCode.append(String.format("%n// %s%n", StreamOperation.getDisplayName(this.terminalOperation)));
		sourceCode.append(this.terminalOperation.getSourceCode().lines().collect(Collectors.joining("\n", "", ";")));

		return sourceCode.toString();
	}
}
