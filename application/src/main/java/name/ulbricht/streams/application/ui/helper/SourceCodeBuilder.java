package name.ulbricht.streams.application.ui.helper;

import java.util.Objects;
import java.util.stream.Collectors;

import name.ulbricht.streams.api.StreamOperationsSet;

public final class SourceCodeBuilder {

	private final StreamOperationsSet operations;

	public SourceCodeBuilder(final StreamOperationsSet operations) {
		this.operations = Objects.requireNonNull(operations, "operations must not be null");
	}

	public String getSourceCode() {
		final var sourceCode = new StringBuilder("/*\n * This code is for demonstration purpose only.\n"
				+ " * It may not be optimal or performant.\n * Please do not use in production.\n */\n\n");

		final var source = this.operations.getSource();
		sourceCode.append(String.format("// %s%n", source.getClass().getSimpleName()));
		sourceCode.append(source.toString().lines().collect(Collectors.joining("\n", "", "\n")));

		sourceCode.append(this.operations.getIntermediats().stream()
				.map(op -> String.format("%n// %s%n%s%n", op.getClass().getSimpleName(), op.toString()))
				.flatMap(String::lines).collect(Collectors.joining("\n", "", "\n")));

		final var terminal = this.operations.getTerminal();
		sourceCode.append(String.format("%n// %s%n", terminal.getClass().getSimpleName()));
		sourceCode.append(terminal.toString().lines().collect(Collectors.joining("\n", "", ";")));

		return sourceCode.toString();
	}
}
