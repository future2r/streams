package name.ulbricht.streams.api;

import java.util.Objects;
import java.util.function.Supplier;

public final class StreamOperationsPreset {

	private final String name;
	private final Supplier<StreamOperationsSet> operations;

	public StreamOperationsPreset(final String name, final Supplier<StreamOperationsSet> operations) {
		this.name = Objects.requireNonNull(name, "name must not be null");
		this.operations = Objects.requireNonNull(operations, "operations must not be null");
	}

	public String getName() {
		return this.name;
	}

	public StreamOperationsSet getOperations() {
		return this.operations.get();
	}
}
