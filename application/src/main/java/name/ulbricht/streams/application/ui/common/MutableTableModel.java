package name.ulbricht.streams.application.ui.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class MutableTableModel<T> implements TableModel {

	public static final class Column<T> {

		private final String columnName;
		private final Class<?> columnClass;
		private final Function<T, ?> valueAccessor;

		public Column(final String columnName) {
			this(columnName, null, null);
		}

		public Column(final String columnName, final Function<T, ?> valueAccessor) {
			this(columnName, valueAccessor, null);
		}

		public Column(final String columnName, final Function<T, ?> valueAccessor, final Class<?> columnClass) {
			this.columnName = Objects.requireNonNull(columnName, "columnName must not be null");
			this.valueAccessor = valueAccessor;
			this.columnClass = columnClass;
		}

		public String getColumnName() {
			return this.columnName;
		}

		public Class<?> getColumnClass() {
			return this.columnClass != null ? this.columnClass : Object.class;
		}

		public Object getValue(final T logger) {
			return this.valueAccessor != null ? this.valueAccessor.apply(logger) : null;
		}
	}

	private final List<Column<T>> columns = new ArrayList<>();
	private final List<T> rows = new ArrayList<>();
	private final EventListenerList eventListeners = new EventListenerList();

	public MutableTableModel(final List<Column<T>> columns) {
		this(columns, List.of());
	}

	public MutableTableModel(final List<Column<T>> columns, final List<T> initialRows) {
		this.columns.addAll(Objects.requireNonNull(columns, "columns must not be null"));
		this.rows.addAll(Objects.requireNonNull(initialRows, "initialRows must not be null"));
	}

	@Override
	public int getColumnCount() {
		return this.columns.size();
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return this.columns.get(columnIndex).getColumnName();
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		return this.columns.get(columnIndex).getColumnClass();
	}

	@Override
	public int getRowCount() {
		return this.rows.size();
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return this.columns.get(columnIndex).getValue(this.rows.get(rowIndex));
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

	public T getRow(final int rowIndex) {
		return this.rows.get(rowIndex);
	}

	public void removeAll() {
		if (!this.rows.isEmpty()) {
			final var rowCount = this.rows.size();
			this.rows.clear();
			fireRowsDeleted(0, rowCount - 1);
		}
	}

	public void addAll(final List<T> newRows) {
		if (!newRows.isEmpty()) {
			final int size = this.rows.size();
			this.rows.addAll(newRows);
			fireRowsInserted(size, this.rows.size() - 1);
		}
	}

	public void replaceAll(final List<T> newRows) {
		removeAll();
		addAll(newRows);
	}

	public void update(final T row) {
		final var rowIndex = this.rows.indexOf(row);
		fireRowUpdated(rowIndex);
	}

	private void fireRowsInserted(final int firstRow, final int lastRow) {
		fireTableChanged(firstRow, lastRow, TableModelEvent.INSERT);
	}

	private void fireRowsDeleted(final int firstRow, final int lastRow) {
		fireTableChanged(firstRow, lastRow, TableModelEvent.DELETE);
	}

	private void fireRowUpdated(final int row) {
		fireRowsUpdated(row, row);
	}

	private void fireRowsUpdated(final int firstRow, final int lastRow) {
		fireTableChanged(firstRow, lastRow, TableModelEvent.UPDATE);
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