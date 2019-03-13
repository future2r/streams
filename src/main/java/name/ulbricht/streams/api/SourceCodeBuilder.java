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

		final var source = this.operations.getSource();
		sourceCode.append(String.format("// %s%n", StreamOperations.getDisplayName(source.getClass())));
		sourceCode.append(source.toString().lines().collect(Collectors.joining("\n", "", "\n")));

		sourceCode.append(this.operations.getIntermediats().stream().map(
				op -> String.format("%n// %s%n%s%n", StreamOperations.getDisplayName(op.getClass()), op.toString()))
				.flatMap(String::lines).collect(Collectors.joining("\n", "", "\n")));

		final var terminal = this.operations.getTerminal();
		sourceCode.append(String.format("%n// %s%n", StreamOperations.getDisplayName(terminal.getClass())));
		sourceCode.append(terminal.toString().lines().collect(Collectors.joining("\n", "", ";")));

		return sourceCode.toString();
	}
}
