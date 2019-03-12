package name.ulbricht.streams.api;

import java.util.Objects;
import java.util.stream.Collectors;

public final class SourceCodeBuilder {

	private final StreamOperationSet operations;

	public SourceCodeBuilder(final StreamOperationSet operations) {
		this.operations = Objects.requireNonNull(operations, "operations must not be null");
	}

	public String getSourceCode() {
		final var sourceCode = new StringBuilder();

		sourceCode
				.append(String.format("// %s%n", StreamOperation.getDisplayName(this.operations.getSourceOperation())));
		sourceCode.append(this.operations.getSourceOperation().getSourceCode().lines()
				.collect(Collectors.joining("\n", "", "\n")));

		sourceCode.append(this.operations.getIntermediatOperations().stream()
				.map(op -> String.format("%n// %s%n%s%n", StreamOperation.getDisplayName(op), op.getSourceCode()))
				.flatMap(String::lines).collect(Collectors.joining("\n", "", "\n")));

		sourceCode.append(
				String.format("%n// %s%n", StreamOperation.getDisplayName(this.operations.getTerminalOperation())));
		sourceCode.append(this.operations.getTerminalOperation().getSourceCode().lines()
				.collect(Collectors.joining("\n", "", ";")));

		return sourceCode.toString();
	}
}
