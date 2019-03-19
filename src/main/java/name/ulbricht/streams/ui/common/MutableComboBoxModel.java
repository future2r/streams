package name.ulbricht.streams.ui.common;

import javax.swing.ComboBoxModel;

public class MutableComboBoxModel<T> extends MutableListModel<T> implements ComboBoxModel<T> {

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
