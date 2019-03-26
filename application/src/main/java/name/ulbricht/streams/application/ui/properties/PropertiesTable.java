package name.ulbricht.streams.application.ui.properties;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public final class PropertiesTable extends JTable {

	public PropertiesTable() {
		super(new PropertiesTableModel());

		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		final var nameColumn = getColumnModel().getColumn(0);
		nameColumn.setCellRenderer(new PropertyNameTableCellRenderer());

		final var valueColumn = getColumnModel().getColumn(1);
		valueColumn.setCellRenderer(new PropertyValueTableCellRenderer());
		valueColumn.setCellEditor(new PropertyValueTableCellEditor());
	}

	@Override
	public PropertiesTableModel getModel() {
		return (PropertiesTableModel) super.getModel();
	}
}
