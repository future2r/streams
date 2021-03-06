package name.ulbricht.streams.application.ui.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class MutableListModel<T> implements ListModel<T> {

	private final List<T> elements = new ArrayList<>();
	private final EventListenerList eventListers = new EventListenerList();

	@SafeVarargs
	public MutableListModel(final T... initialElements) {
		this(List.of(Objects.requireNonNull(initialElements, "initialElements must not be null")));
	}

	public MutableListModel(final List<T> initialElements) {
		this.elements.addAll(Objects.requireNonNull(initialElements, "initialElements must not be null"));
	}

	@Override
	public int getSize() {
		return this.elements.size();
	}

	@Override
	public T getElementAt(final int index) {
		return this.elements.get(index);
	}

	public List<T> getAllElements() {
		return Collections.unmodifiableList(this.elements);
	}

	public void addElement(final T element) {
		addElementAt(this.elements.size(), element);
	}

	public void addElementAt(final int index, final T element) {
		this.elements.add(index, element);
		fireElementAdded(index);
	}

	public void removeElementAt(final int index) {
		if (index >= 0 && index < this.elements.size()) {
			this.elements.remove(index);
			fireElementRemoved(index);
		}
	}

	public void removeAllElements() {
		if (!this.elements.isEmpty()) {
			int toIndex = this.elements.size() - 1;
			this.elements.clear();
			fireIntervalRemoved(0, toIndex);
		}
	}

	public void replaceAllElements(final List<T> newElemens) {
		removeAllElements();
		if (!newElemens.isEmpty()) {
			this.elements.addAll(newElemens);
			fireIntervalAdded(0, this.elements.size() - 1);
		}
	}

	public void moveElementUp(final int index) {
		if (index > 0) {
			final var previousIndex = index - 1;
			final var element = this.elements.get(index);
			final var beforeElement = this.elements.get(previousIndex);

			this.elements.set(index, beforeElement);
			this.elements.set(previousIndex, element);

			fireContentsChanged(previousIndex, index);
		}
	}

	public void moveElementDown(final int index) {
		if (index >= 0 && index < this.elements.size() - 1) {
			final var nextIndex = index + 1;
			final var element = this.elements.get(index);
			final var nextElement = this.elements.get(nextIndex);

			this.elements.set(index, nextElement);
			this.elements.set(nextIndex, element);

			fireContentsChanged(index, nextIndex);
		}
	}

	public void updateElement(final int index) {
		fireElementChanged(index);
	}

	public void updateElement(final T element) {
		final int index = this.elements.indexOf(element);
		if (index >= 0)
			updateElement(index);
	}

	@Override
	public void addListDataListener(final ListDataListener l) {
		this.eventListers.add(ListDataListener.class, l);
	}

	@Override
	public void removeListDataListener(final ListDataListener l) {
		this.eventListers.remove(ListDataListener.class, l);
	}

	private void fireElementAdded(final int index) {
		fireIntervalAdded(index, index);
	}

	private void fireIntervalAdded(final int fromIndex, final int toIndex) {
		final var listeners = this.eventListers.getListeners(ListDataListener.class);
		if (listeners.length > 0) {
			final var e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, fromIndex, toIndex);
			for (final var listener : listeners) {
				listener.intervalAdded(e);
			}
		}
	}

	private void fireElementRemoved(final int index) {
		fireIntervalRemoved(index, index);
	}

	private void fireIntervalRemoved(final int fromIndex, final int toIndex) {
		final var listeners = this.eventListers.getListeners(ListDataListener.class);
		if (listeners.length > 0) {
			final var e = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, fromIndex, toIndex);
			for (final var listener : listeners) {
				listener.intervalRemoved(e);
			}
		}
	}

	private void fireElementChanged(final int index) {
		fireContentsChanged(index, index);
	}

	private void fireContentsChanged(final int fromIndex, final int toIndex) {
		final var listeners = this.eventListers.getListeners(ListDataListener.class);
		if (listeners.length > 0) {
			final var e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, fromIndex, toIndex);
			for (final var listener : listeners) {
				listener.contentsChanged(e);
			}
		}
	}
}
