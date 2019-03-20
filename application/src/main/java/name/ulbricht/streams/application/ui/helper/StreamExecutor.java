package name.ulbricht.streams.application.ui.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperationException;
import name.ulbricht.streams.api.StreamOperationSet;

public final class StreamExecutor {

	private static final Logger log = Logger.getLogger("name.ulbricht.streams");

	public static class ExecutionLogger implements Consumer<Object> {

		private volatile long elementsProvided;
		private final Object operation;
		private final Consumer<ExecutionLogger> updateHandler;

		private ExecutionLogger(final Object operation, final Consumer<ExecutionLogger> updateHandler) {
			this.operation = operation;
			this.updateHandler = updateHandler;
		}

		@Override
		public void accept(final Object element) {
			this.elementsProvided++;
			this.updateHandler.accept(this);
			log.info(() -> String.format("%s: %s", this.operation.getClass().getSimpleName(), element));
		}

		public Object getOperation() {
			return this.operation;
		}

		public long getElementsProvided() {
			return this.elementsProvided;
		}
	}

	private final StreamOperationSet operations;

	@SuppressWarnings("rawtypes")
	private final Stream stream;
	private boolean executed;

	private final List<ExecutionLogger> executionLoggers = new ArrayList<>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StreamExecutor(final StreamOperationSet operations, final Consumer<ExecutionLogger> updateHandler) {
		this.operations = Objects.requireNonNull(operations, "operations must not be null");

		final var sourceOperation = this.operations.getSource();
		Stream tempStream = addExecutionLogger(((Supplier<Stream>) sourceOperation).get(), sourceOperation,
				updateHandler);

		for (final var intermediatOperation : this.operations.getIntermediats()) {
			tempStream = addExecutionLogger(((Function<Stream, Stream>) intermediatOperation).apply(tempStream),
					intermediatOperation, updateHandler);
		}

		this.stream = tempStream;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object execute() {
		if (this.executed)
			throw new StreamOperationException("Pipeline was already executed.");
		this.executionLoggers.clear();

		return ((Function<Stream, Object>) this.operations.getTerminal()).apply(stream);
	}

	private Stream<?> addExecutionLogger(final Stream<?> stream, final Object operation,
			final Consumer<ExecutionLogger> updateHandler) {
		final var executionLogger = new ExecutionLogger(operation, updateHandler);
		this.executionLoggers.add(executionLogger);
		return stream.peek(executionLogger);
	}

	public List<ExecutionLogger> getExecutionLoggers() {
		return Collections.unmodifiableList(this.executionLoggers);
	}
}
