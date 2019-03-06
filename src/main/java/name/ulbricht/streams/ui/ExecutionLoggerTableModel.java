package name.ulbricht.streams.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import name.ulbricht.streams.api.StreamExecutor;
import name.ulbricht.streams.api.StreamExecutor.ExecutionLogger;

final class ExecutionLoggerTableModel implements TableModel {

	private enum Column {
		NAME(String.class, ExecutionLogger::getOperationName),
		ELEMENTS(Long.class, ExecutionLogger::getElementsProvided);

		private final Class<?> columnClass;
		private final Function<ExecutionLogger, ?> valueAccessor;

		Column(final Class<?> columnClass, final Function<ExecutionLogger, ?> valueAccessor) {
			this.columnClass = columnClass;
			this.valueAccessor = valueAccessor;
		}

		String getColumnName() {
			return Messages.getString("ExecutionLoggerTableModel." + name() + ".name");
		}

		Class<?> getColumnClass() {
			return this.columnClass;
		}

		Object getValue(final StreamExecutor.ExecutionLogger logger) {
			return this.valueAccessor.apply(logger);
		}
	}

	private final List<StreamExecutor.ExecutionLogger> rows = new ArrayList<>();
	private final EventListenerList eventListeners = new EventListenerList();

	@Override
	public int getColumnCount() {
		return Column.values().length;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return Column.values()[columnIndex].getColumnName();
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		return Column.values()[columnIndex].getColumnClass();
	}

	@Override
	public int getRowCount() {
		return this.rows.size();
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return Column.values()[columnIndex].getValue(this.rows.get(rowIndex));
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addTableModelListener(final TableModelListener l) {
		this.eventListeners.add(TableModelListener.class, l);

	}

	@Override
	public void removeTableModelListener(final TableModelListener l) {
		this.eventListeners.remove(TableModelListener.class, l);
	}

	void removeAll() {
		if (!this.rows.isEmpty()) {
			final var rowCount = this.rows.size();
			this.rows.clear();
			fireRowsDeleted(0, rowCount - 1);
		}
	}

	void replaceAll(final List<StreamExecutor.ExecutionLogger> executionLoggers) {
		removeAll();
		this.rows.addAll(executionLoggers);
		if (!this.rows.isEmpty())
			fireRowsInserted(0, this.rows.size() - 1);
	}

	private void fireRowsInserted(final int firstRow, final int lastRow) {
		fireTableChanged(firstRow, lastRow, TableModelEvent.INSERT);
	}

	private void fireRowsDeleted(final int firstRow, final int lastRow) {
		fireTableChanged(firstRow, lastRow, TableModelEvent.DELETE);
	}

	private void fireTableChanged(final int firstRow, final int lastRow, final int type) {
		final var listeners = this.eventListeners.getListeners(TableModelListener.class);
		if (listeners.length > 0) {
			final var event = new TableModelEvent(this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, type);
			for (final var listener : listeners) {
				listener.tableChanged(event);
			}
		}
	}
}
