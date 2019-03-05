package name.ulbricht.streams.ui;

import java.util.Objects;

import javax.swing.SwingWorker;

import name.ulbricht.streams.api.StreamExecutor;

final class ExecutionWorker extends SwingWorker<Object, Object> {

	private final StreamExecutor executor;

	ExecutionWorker(final StreamExecutor executor) {
		this.executor = Objects.requireNonNull(executor, "executor must not be null");
	}

	@Override
	protected Object doInBackground() throws Exception {
		return this.executor.execute();
	}
}
