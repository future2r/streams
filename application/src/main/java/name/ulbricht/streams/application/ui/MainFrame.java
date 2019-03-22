package name.ulbricht.streams.application.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.module.ModuleDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import name.ulbricht.streams.api.StreamOperationException;
import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.api.basic.Empty;
import name.ulbricht.streams.api.basic.SystemOut;
import name.ulbricht.streams.application.ui.common.MutableComboBoxModel;
import name.ulbricht.streams.application.ui.common.MutableListModel;
import name.ulbricht.streams.application.ui.common.MutableTableModel;
import name.ulbricht.streams.application.ui.helper.SourceCodeBuilder;
import name.ulbricht.streams.application.ui.helper.StreamExecutor;
import name.ulbricht.streams.application.ui.helper.StreamOperations;

@SuppressWarnings("serial")
public final class MainFrame extends JFrame {

	private static final Logger log = Logger.getLogger("name.ulbricht.streams");

	private static MainFrame instance;

	public static synchronized MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}

	private final Actions actions = Actions.of();
	private final JTabbedPane mainTabbedPane;
	private final JPanel executionPanel;

	private MainFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setTitle(Messages.getString("MainFrame.title"));
		setIconImages(Icons.getImages(Icons.APPLICATION));

		setJMenuBar(createMenuBar());

		this.mainTabbedPane = new JTabbedPane();
		this.mainTabbedPane.setFocusable(false);
		addTab(this.mainTabbedPane, createSetupPanel(), "tabSetup.title", Icons.SETUP);
		addTab(this.mainTabbedPane, createCodePanel(), "tabCode.title", Icons.CODE);

		this.executionPanel = createExecutionPanel();
		addTab(this.mainTabbedPane, executionPanel, "tabExecution.title", Icons.EXECUTION);

		final var contentPane = new JPanel(new BorderLayout());
		contentPane.add(this.mainTabbedPane, BorderLayout.CENTER);
		contentPane.add(createToolBar(), BorderLayout.NORTH);

		setContentPane(contentPane);
		pack();

		SwingUtilities
				.invokeLater(() -> presetSelected(new StreamOperationSet(new Empty<>(), List.of(), new SystemOut<>())));
		SwingUtilities.invokeLater(() -> this.sourceOperationComboBox.requestFocusInWindow());
	}

	@Override
	public void dispose() {
		instance = null;
		super.dispose();
	}

	private JMenuBar createMenuBar() {
		final var menuBar = new JMenuBar();

		final var fileMenu = menuBar.add(new JMenu(Messages.getString("fileMenu.text")));
		fileMenu.add(this.actions.add(Actions.action("exit", this::dispose)));

		final var presetsMenu = menuBar.add(new JMenu(Messages.getString("presetsMenu.text")));
		StreamOperations.findPresets().entrySet().stream().forEach(entry -> {
			final var menuItem = presetsMenu
					.add(new JMenuItem(entry.getKey(), Icons.getIcon(Icons.APPLICATION, Icons.Size.X_SMALL)));
			menuItem.addActionListener(e -> presetSelected(entry.getValue().get()));
		});

		final var helpMenu = menuBar.add(new JMenu(Messages.getString("helpMenu.text")));
		helpMenu.add(this.actions.add(Actions.action("about", this::showAbout)));

		return menuBar;
	}

	private JToolBar createToolBar() {
		final var toolBar = new JToolBar();
		toolBar.setFloatable(false);

		toolBar.add(this.actions.add(Actions.action("execute", this::execute, () -> this.executionWorker == null)));
		toolBar.add(this.actions.add(Actions.action("interrupt", this::interrupt, () -> this.executionWorker != null)));

		return toolBar;
	}

	private void presetSelected(final StreamOperationSet preset) {
		final var source = preset.getSource();
		this.sourceOperationComboBox.setSelectedItem(source.getClass());
		setSourceOperation(source);

		this.intermediateOperationListModel.replaceAllElements(preset.getIntermediats());

		final var terminalOperation = preset.getTerminal();
		this.terminalOperationComboBox.setSelectedItem(terminalOperation.getClass());
		setTerminalOperation(terminalOperation);

		this.actions.validate();
	}

	private JPanel createSetupPanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);

		panel.add(createSourcePanel(), new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(createIntermediatePanel(), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(createTerminalPanel(), new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		return panel;
	}

	private MutableComboBoxModel<Class<?>> sourceOperationComboBoxModel;
	private JComboBox<Class<?>> sourceOperationComboBox;
	private StreamOperationPanel sourceOperationPanel;

	private JPanel createSourcePanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(Messages.getString("sourcePanel.title")));

		this.sourceOperationComboBoxModel = new MutableComboBoxModel<>(StreamOperations.findSourceOperations());
		this.sourceOperationComboBox = new JComboBox<>(this.sourceOperationComboBoxModel);
		this.sourceOperationComboBox.setMaximumRowCount(15);
		if (this.sourceOperationComboBoxModel.getSize() > 0)
			this.sourceOperationComboBox.setSelectedIndex(0);
		this.sourceOperationComboBox.setRenderer(new StreamOperationClassListCellRenderer());
		this.sourceOperationComboBox.addActionListener(e -> sourceOperationSelected());

		this.sourceOperationPanel = new StreamOperationPanel();
		this.sourceOperationPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
					configureSourceOperation();
			}
		});

		final var configureButton = new JButton(this.actions.add(Actions.action("configureSourceOperation",
				this::configureSourceOperation, () -> this.currentSourceOperation != null
						&& StreamOperations.hasProperties(this.currentSourceOperation.getClass()))));

		panel.add(this.sourceOperationComboBox, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(configureButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.sourceOperationPanel, new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		return panel;
	}

	private Object currentSourceOperation;

	private void sourceOperationSelected() {
		final var selectedSourceOperationClass = (Class<?>) this.sourceOperationComboBox.getSelectedItem();

		if (selectedSourceOperationClass != null) {
			try {
				setSourceOperation(StreamOperations.createOperation(selectedSourceOperationClass));
			} catch (final StreamOperationException ex) {
				Alerts.showError(this, ex);
			}
		}
	}

	private void setSourceOperation(final Object sourceOperation) {
		this.currentSourceOperation = sourceOperation;
		this.sourceOperationPanel.updateContent(this.currentSourceOperation);
		streamSetupChanged();
		this.actions.validate();
	}

	private void configureSourceOperation() {
		if (this.currentSourceOperation != null
				&& StreamOperations.hasProperties(this.currentSourceOperation.getClass())) {
			if (showConfigureDialog(this.currentSourceOperation)) {
				this.sourceOperationPanel.updateContent(this.currentSourceOperation);
			}
		}
	}

	private MutableComboBoxModel<Class<?>> intermediateOperationComboBoxModel;
	private JComboBox<Class<?>> intermediateOperationComboBox;
	private MutableListModel<Object> intermediateOperationListModel;
	private JList<Object> intermediateOperationList;

	private JPanel createIntermediatePanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(Messages.getString("intermediatePanel.title")));

		this.intermediateOperationComboBoxModel = new MutableComboBoxModel<>(
				StreamOperations.findIntermediateOperations());
		this.intermediateOperationComboBox = new JComboBox<>(this.intermediateOperationComboBoxModel);
		this.intermediateOperationComboBox.setMaximumRowCount(15);
		if (this.intermediateOperationComboBoxModel.getSize() > 0)
			this.intermediateOperationComboBox.setSelectedIndex(0);
		this.intermediateOperationComboBox.setRenderer(new StreamOperationClassListCellRenderer());

		final var addButton = new JButton(
				this.actions.add(Actions.action("addIntermediateOperation", e -> addIntermediateOperation())));

		this.intermediateOperationListModel = new MutableListModel<>();
		this.intermediateOperationList = new JList<>(this.intermediateOperationListModel);
		this.intermediateOperationList.setCellRenderer(new StreamOperationListCellRenderer());
		this.intermediateOperationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.intermediateOperationList.getSelectionModel().addListSelectionListener(e -> this.actions.validate());
		this.intermediateOperationList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
					configureIntermediateOperation();
			}
		});

		final var moveUpButton = new JButton(this.actions.add(Actions.action("moveIntermediateOperationUp",
				this::moveIntermediateOperationUp, this::canMoveIntermediateOperationUp)));
		final var moveDownButton = new JButton(this.actions.add(Actions.action("moveIntermediateOperationDown",
				this::moveIntermediateOperationDown, this::canMoveIntermediateOperationDown)));
		final var removeButton = new JButton(this.actions.add(Actions.action("removeIntermediateOperation",
				this::removeIntermediateOperation, this::isIntermediateOperationSelected)));
		final var removeAllButton = new JButton(this.actions.add(Actions.action("removeAllIntermediateOperations",
				this::removeAllIntermediateOperations, this::existIntermediateOperations)));
		final var configureButton = new JButton(this.actions.add(Actions.action("configureIntermediateOperation",
				this::configureIntermediateOperation, this::isIntermediateOperationConfigurable)));

		panel.add(this.intermediateOperationComboBox, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(addButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(new JScrollPane(this.intermediateOperationList), new GridBagConstraints(0, 1, 1, 10, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));

		var buttonRow = 1;
		panel.add(moveUpButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(moveDownButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(removeButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(removeAllButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(configureButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		return panel;
	}

	private void addIntermediateOperation() {
		final var selectedOperationClass = (Class<?>) this.intermediateOperationComboBox.getSelectedItem();

		if (selectedOperationClass != null) {
			try {
				final var operation = StreamOperations.createOperation(selectedOperationClass);

				final var selectedIndex = this.intermediateOperationList.getSelectedIndex();
				if (selectedIndex >= 0) {
					final var insertIndex = selectedIndex + 1;
					this.intermediateOperationListModel.addElementAt(insertIndex, operation);
					this.intermediateOperationList.setSelectedIndex(insertIndex);
				} else {
					this.intermediateOperationListModel.addElement(operation);
					this.intermediateOperationList.setSelectedIndex(this.intermediateOperationListModel.getSize() - 1);
				}

				streamSetupChanged();
			} catch (final StreamOperationException ex) {
				Alerts.showError(this, ex);
			}
		}

		this.actions.validate();
	}

	private void moveIntermediateOperationUp() {
		final var selectedIndex = this.intermediateOperationList.getSelectedIndex();
		if (selectedIndex > 0) {
			this.intermediateOperationListModel.moveElementUp(selectedIndex);
			this.intermediateOperationList.setSelectedIndex(selectedIndex - 1);
			streamSetupChanged();
		}
	}

	private void moveIntermediateOperationDown() {
		final var selectedIndex = this.intermediateOperationList.getSelectedIndex();
		if (selectedIndex >= 0 && selectedIndex < this.intermediateOperationListModel.getSize() - 1) {
			this.intermediateOperationListModel.moveElementDown(selectedIndex);
			this.intermediateOperationList.setSelectedIndex(selectedIndex + 1);
			streamSetupChanged();
		}
	}

	private void removeIntermediateOperation() {
		final var selectedIndex = this.intermediateOperationList.getSelectedIndex();
		if (selectedIndex >= 0) {
			this.intermediateOperationListModel.removeElementAt(selectedIndex);
			this.actions.validate();
			streamSetupChanged();
		}
	}

	private void removeAllIntermediateOperations() {
		if (this.intermediateOperationListModel.getSize() > 0) {
			this.intermediateOperationListModel.removeAllElements();
			this.actions.validate();
			streamSetupChanged();
		}
	}

	private void configureIntermediateOperation() {
		final var intermediateOperation = this.intermediateOperationList.getSelectedValue();
		if (intermediateOperation != null && StreamOperations.hasProperties(intermediateOperation.getClass())) {
			if (showConfigureDialog(intermediateOperation)) {
				this.intermediateOperationListModel.updateElement(intermediateOperation);
			}
		}
	}

	private Optional<Object> getSelectedIntermediateOperation() {
		return Optional.ofNullable(this.intermediateOperationList.getSelectedValue());
	}

	private boolean isIntermediateOperationSelected() {
		return getSelectedIntermediateOperation().isPresent();
	}

	private boolean canMoveIntermediateOperationUp() {
		return this.intermediateOperationList.getSelectedIndex() > 0;
	}

	private boolean canMoveIntermediateOperationDown() {
		final var size = this.intermediateOperationListModel.getSize();
		final var selectedIndex = this.intermediateOperationList.getSelectedIndex();
		return selectedIndex >= 0 && selectedIndex < (size - 1);
	}

	private boolean existIntermediateOperations() {
		return this.intermediateOperationListModel.getSize() > 0;
	}

	private boolean isIntermediateOperationConfigurable() {
		return getSelectedIntermediateOperation().map(Object::getClass).map(StreamOperations::hasProperties)
				.orElse(Boolean.FALSE);
	}

	private MutableComboBoxModel<Class<?>> terminalOperationComboBoxModel;
	private JComboBox<Class<?>> terminalOperationComboBox;
	private StreamOperationPanel terminalOperationPanel;
	private Object currentTerminalOperation;

	private JPanel createTerminalPanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(Messages.getString("terminalPanel.title")));

		this.terminalOperationComboBoxModel = new MutableComboBoxModel<>(StreamOperations.findTerminalOperations());
		this.terminalOperationComboBox = new JComboBox<>(this.terminalOperationComboBoxModel);
		this.terminalOperationComboBox.setMaximumRowCount(15);
		if (this.terminalOperationComboBoxModel.getSize() > 0)
			this.terminalOperationComboBox.setSelectedIndex(0);
		this.terminalOperationComboBox.setRenderer(new StreamOperationClassListCellRenderer());
		this.terminalOperationComboBox.addActionListener(e -> terminalOperationSelected());

		this.terminalOperationPanel = new StreamOperationPanel();
		this.terminalOperationPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
					configureTerminalOperation();
			}
		});

		final var configureButton = new JButton(this.actions.add(Actions.action("configureTerminalOperation",
				this::configureTerminalOperation, () -> this.currentTerminalOperation != null
						&& StreamOperations.hasProperties(this.currentTerminalOperation.getClass()))));

		panel.add(this.terminalOperationComboBox, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(configureButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.terminalOperationPanel, new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		return panel;
	}

	private void terminalOperationSelected() {
		final var selectedTerminalOperation = (Class<?>) this.terminalOperationComboBox.getSelectedItem();

		if (selectedTerminalOperation != null) {
			try {
				setTerminalOperation(StreamOperations.createOperation(selectedTerminalOperation));
			} catch (final StreamOperationException ex) {
				Alerts.showError(this, ex);
			}
		}
	}

	private void setTerminalOperation(final Object terminalOperation) {
		this.currentTerminalOperation = terminalOperation;
		this.terminalOperationPanel.updateContent(this.currentTerminalOperation);
		streamSetupChanged();
		this.actions.validate();
	}

	private void configureTerminalOperation() {
		if (this.currentTerminalOperation != null
				&& StreamOperations.hasProperties(this.currentTerminalOperation.getClass())) {
			if (showConfigureDialog(this.currentTerminalOperation)) {
				this.terminalOperationPanel.updateContent(this.currentTerminalOperation);
			}
		}
	}

	private boolean showConfigureDialog(final Object operation) {
		if (ConfigurationDialog.showModal(this, operation)) {
			streamSetupChanged();
			return true;
		}
		return false;
	}

	private void streamSetupChanged() {
		updateSourceCode();
	}

	private JTextArea codeTextArea;

	private JPanel createCodePanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);

		this.codeTextArea = new JTextArea();
		this.codeTextArea.setEditable(false);

		final var copyCodeButton = new JButton(Actions.action("copyCode", this::copyCode));

		panel.add(new JScrollPane(this.codeTextArea), new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(copyCodeButton, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

		return panel;
	}

	private void copyCode() {
		this.codeTextArea.selectAll();
		this.codeTextArea.copy();
	}

	private void updateSourceCode() {
		if (this.currentSourceOperation != null && this.currentTerminalOperation != null) {
			final var operations = new StreamOperationSet(this.currentSourceOperation,
					this.intermediateOperationListModel.getAllElements(), this.currentTerminalOperation);
			SourceCodeBuilder builder = new SourceCodeBuilder(operations);
			this.codeTextArea.setText(builder.getSourceCode());
		}
	}

	private JTextArea sysOutTextArea;
	private JTextArea logTextArea;
	private MutableTableModel<StreamExecutor.ExecutionLogger> statisticsTableModel;
	private JTable statisticsTable;

	private JPanel createExecutionPanel() {
		final var panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		this.sysOutTextArea = new JTextArea();
		this.sysOutTextArea.setEditable(false);

		System.setOut(new TextAreaPrintStream(System.out, this.sysOutTextArea));

		this.logTextArea = new JTextArea();
		this.logTextArea.setEditable(false);

		log.addHandler(new TextAreaLogHandler(this.logTextArea));

		this.statisticsTableModel = new MutableTableModel<>(List.of(
				new MutableTableModel.Column<>(Messages.getString("statisticsTableModel.nameColumn"),
						l -> l.getOperation().getClass().getSimpleName(), String.class),
				new MutableTableModel.Column<>(Messages.getString("statisticsTableModel.elementsColumn"),
						StreamExecutor.ExecutionLogger::getElementsProvided, Long.class)));
		this.statisticsTable = new JTable(this.statisticsTableModel);
		final var nameColumn = this.statisticsTable.getColumnModel().getColumn(0);
		nameColumn.setCellRenderer(new StreamOperationTableCellRenderer());

		final var tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setFocusable(false);
		addTab(tabbedPane, new JScrollPane(this.statisticsTable), "tabStatistics.title", Icons.STATISTICS);
		addTab(tabbedPane, new JScrollPane(this.logTextArea), "tabLog.title", Icons.LOG);
		addTab(tabbedPane, new JScrollPane(this.sysOutTextArea), "tabSysOut.title", Icons.CONSOLE);

		panel.add(tabbedPane, BorderLayout.CENTER);

		return panel;
	}

	private ExecutionWorker executionWorker;

	private void execute() {
		if (this.executionWorker == null) {
			this.logTextArea.setText("");
			this.sysOutTextArea.setText("");
			this.statisticsTableModel.removeAll();

			final var operations = new StreamOperationSet(this.currentSourceOperation,
					this.intermediateOperationListModel.getAllElements(), this.currentTerminalOperation);
			final var executor = new StreamExecutor(operations,
					logger -> SwingUtilities.invokeLater(() -> this.statisticsTableModel.update(logger)));

			this.executionWorker = new ExecutionWorker(executor);
			this.executionWorker.addPropertyChangeListener(e -> {
				if (e.getSource() == this.executionWorker && e.getPropertyName().equals("state")) {
					workerStateChanged((ExecutionWorker) e.getSource(), (SwingWorker.StateValue) e.getNewValue());
				}
			});

			this.statisticsTableModel.replaceAll(this.executionWorker.getExecutor().getExecutionLoggers());
			this.mainTabbedPane.setSelectedComponent(this.executionPanel);

			this.actions.validate();

			this.executionWorker.execute();
		}
	}

	private void workerStateChanged(final ExecutionWorker worker, final SwingWorker.StateValue state) {
		switch (state) {
		case DONE:
			this.executionWorker = null;

			try {
				final var result = worker.get();
				log.info(() -> String.format("Result (class: %s): %s",
						result != null ? result.getClass().getName() : "n/a", toString(result)));
			} catch (final InterruptedException | ExecutionException ex) {
				Alerts.showError(this, ex);
			}

			this.actions.validate();
			break;
		default: // ignore
		}
	}

	private void interrupt() {
		Alerts.showError(this, Messages.getString("interrupt.message"));
	}

	private static String toString(final Object result) {
		if (result != null && result.getClass().isArray())
			return Arrays.toString((Object[]) result);
		return Objects.toString(result);
	}

	private static void addTab(final JTabbedPane tabbedPane, final Component component, final String titleResource,
			final String iconResource) {
		tabbedPane.add(Messages.getString(titleResource), component);
		final var tabIndex = tabbedPane.indexOfComponent(component);
		tabbedPane.setIconAt(tabIndex, Icons.getIcon(iconResource, Icons.Size.X_SMALL));
	}

	private void showAbout() {
		final var applicationName = Messages.getString("MainFrame.title");
		final var version = ModuleLayer.boot().findModule("name.ulbricht.streams").map(Module::getDescriptor)
				.flatMap(ModuleDescriptor::version).map(ModuleDescriptor.Version::toString).orElse("?");
		final var jvmName = System.getProperty("java.vm.name");
		final var javaVersion = System.getProperty("java.vm.version");

		final var title = String.format(Messages.getString("about.titlePattern"), applicationName);

		final var message = String.format(Messages.getString("about.messagePattern"), applicationName, version, jvmName,
				javaVersion);

		JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}