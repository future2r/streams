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

		sourceCode.append(String.format("// %s%n", StreamOperation.getDisplayName(this.operations.getSource())));
		sourceCode.append(
				this.operations.getSource().getSourceCode().lines().collect(Collectors.joining("\n", "", "\n")));

		sourceCode.append(this.operations.getIntermediats().stream()
				.map(op -> String.format("%n// %s%n%s%n", StreamOperation.getDisplayName(op), op.getSourceCode()))
				.flatMap(String::lines).collect(Collectors.joining("\n", "", "\n")));

		sourceCode.append(String.format("%n// %s%n", StreamOperation.getDisplayName(this.operations.getTerminal())));
		sourceCode.append(
				this.operations.getTerminal().getSourceCode().lines().collect(Collectors.joining("\n", "", ";")));

		return sourceCode.toString();
	}
}
