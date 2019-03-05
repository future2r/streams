package name.ulbricht.streams.ui;

import javax.swing.ComboBoxModel;

final class MutableComboBoxModel<T> extends MutableListModel<T> implements ComboBoxModel<T> {

	private T selectedItem;

	@SafeVarargs
	public MutableComboBoxModel(final T... initialElements) {
		super(initialElements);
	}

	@Override
	public T getSelectedItem() {
		return this.selectedItem;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSelectedItem(final Object anItem) {
		this.selectedItem = (T) anItem;
	}
}
