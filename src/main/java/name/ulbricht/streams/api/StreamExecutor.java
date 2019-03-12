package name.ulbricht.streams.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Stream;

public final class StreamExecutor {

	private static final Logger log = Logger.getLogger("name.ulbricht.streams");

	public static class ExecutionLogger implements Consumer<Object> {

		private long elementsProvided;
		private final StreamOperation operation;
		private final String operationName;

		private ExecutionLogger(final StreamOperation operation) {
			this.operation = operation;
			this.operationName = StreamOperation.getDisplayName(this.operation);
		}

		@Override
		public void accept(final Object element) {
			this.elementsProvided++;
			log.info(() -> String.format("%s: %s", this.operationName, element));
		}

		public StreamOperation getOperation() {
			return this.operation;
		}

		public String getOperationName() {
			return this.operationName;
		}

		public long getElementsProvided() {
			return this.elementsProvided;
		}
	}

	private final StreamOperationSet operations;
	private final List<ExecutionLogger> executionLoggers = new ArrayList<>();

	public StreamExecutor(final StreamOperationSet operations) {
		this.operations = Objects.requireNonNull(operations, "operations must not be null");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object execute() {
		this.executionLoggers.clear();

		final var source = this.operations.getSource();
		Stream stream = source.createStream();
		stream = addExecutionLogger(stream, source);

		for (final var intermediatOperation : this.operations.getIntermediats()) {
			stream = intermediatOperation.processStream(stream);
			stream = addExecutionLogger(stream, intermediatOperation);
		}

		return this.operations.getTerminal().terminateStream(stream);
	}

	private Stream<?> addExecutionLogger(final Stream<?> stream, final StreamOperation operation) {
		final var executionLogger = new ExecutionLogger(operation);
		this.executionLoggers.add(executionLogger);
		return stream.peek(executionLogger);
	}

	public List<ExecutionLogger> getExecutionLoggers() {
		return Collections.unmodifiableList(this.executionLoggers);
	}
}
