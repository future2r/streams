package name.ulbricht.streams.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Stream;

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
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.SourceCodeBuilder;
import name.ulbricht.streams.api.StreamExecutor;
import name.ulbricht.streams.api.StreamOperation;
import name.ulbricht.streams.api.StreamOperationException;
import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.api.StreamSource;
import name.ulbricht.streams.api.TerminalOperation;
import name.ulbricht.streams.impl.Preset;
import name.ulbricht.streams.impl.intermediate.IntermediateOperations;
import name.ulbricht.streams.impl.source.StreamSources;
import name.ulbricht.streams.impl.terminal.TerminalOperations;

public final class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger("name.ulbricht.streams");

	private static MainFrame instance;

	public static synchronized MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}

	private final Actions actions = Actions.of();

	private MainFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setTitle(Messages.getString("MainFrame.title"));
		setIconImages(Images.getImages(Images.APPLICATION));

		setJMenuBar(createMenuBar());

		final var tabbedPane = new JTabbedPane();
		tabbedPane.setFocusable(false);
		addTab(tabbedPane, createSetupPanel(), "tabSetup.title", Images.SETUP);
		addTab(tabbedPane, createCodePanel(), "tabCode.title", Images.CODE);
		addTab(tabbedPane, createExecutionPanel(), "tabExecution.title", Images.EXECUTION);

		final var contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		setContentPane(contentPane);
		pack();
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
		Stream.of(Preset.values()).forEach(preset -> {
			final var menuItem = presetsMenu.add(new JMenuItem(preset.getDisplayName()));
			menuItem.addActionListener(e -> presetSelected(preset));
		});

		final var helpMenu = menuBar.add(new JMenu(Messages.getString("helpMenu.text")));
		helpMenu.add(this.actions.add(Actions.action("about", this::showAbout)));

		return menuBar;
	}

	private void presetSelected(final Preset preset) {
		final var operations = preset.operations();

		setStreamSource(operations.getStreamSource());
		this.intermediateOperationListModel.replaceAllElements(operations.getIntermediatOperations());
		setTerminalOperation(operations.getTerminalOperation());

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

	private MutableComboBoxModel<Class<? extends StreamSource<?>>> streamSourceComboBoxModel;
	private JComboBox<Class<? extends StreamSource<?>>> streamSourceComboBox;
	private StreamOperationPanel streamSourcePanel;

	private JPanel createSourcePanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(Messages.getString("sourcePanel.title")));

		this.streamSourceComboBoxModel = new MutableComboBoxModel<>(StreamSources.IMPLEMENTATIONS);
		this.streamSourceComboBox = new JComboBox<>(this.streamSourceComboBoxModel);
		this.streamSourceComboBox.setSelectedIndex(0);
		this.streamSourceComboBox.setRenderer(new StreamOperationClassListCellRenderer());
		this.streamSourceComboBox.addActionListener(e -> streamSourceSelected());

		this.streamSourcePanel = new StreamOperationPanel();

		final var configureStreamSourceButton = new JButton(this.actions.add(Actions.action("configureStreamSource",
				this::configureStreamSource, () -> this.currentStreamSource != null
						&& StreamOperation.supportsConfiguration(this.currentStreamSource))));

		panel.add(this.streamSourceComboBox, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(configureStreamSourceButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.streamSourcePanel, new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		SwingUtilities.invokeLater(this::streamSourceSelected);

		return panel;
	}

	private StreamSource<?> currentStreamSource;

	private void streamSourceSelected() {
		@SuppressWarnings("unchecked")
		final var selectedStreamSourceClass = (Class<? extends StreamSource<?>>) this.streamSourceComboBox
				.getSelectedItem();

		if (selectedStreamSourceClass != null) {
			try {
				setStreamSource(StreamOperation.createOperation(selectedStreamSourceClass));
			} catch (final StreamOperationException ex) {
				Alerts.showError(this, ex);
			}
		}
	}

	private void setStreamSource(final StreamSource<?> streamSource) {
		this.currentStreamSource = streamSource;
		this.streamSourcePanel.updateContent(this.currentStreamSource);
		streamSetupChanged();
		this.actions.validate();
	}

	private void configureStreamSource() {
		if (this.currentStreamSource != null) {
			if (showConfigureDialog(this.currentStreamSource)) {
				this.streamSourcePanel.updateContent(this.currentStreamSource);
			}
		}
	}

	private MutableComboBoxModel<Class<? extends IntermediateOperation<?, ?>>> intermediateOperationComboBoxModel;
	private JComboBox<Class<? extends IntermediateOperation<?, ?>>> intermediateOperationComboBox;
	private MutableListModel<IntermediateOperation<?, ?>> intermediateOperationListModel;
	private JList<IntermediateOperation<?, ?>> intermediateOperationList;

	private JPanel createIntermediatePanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(Messages.getString("intermediatePanel.title")));

		this.intermediateOperationComboBoxModel = new MutableComboBoxModel<>(IntermediateOperations.IMPLEMENTATIONS);
		this.intermediateOperationComboBox = new JComboBox<>(this.intermediateOperationComboBoxModel);
		this.intermediateOperationComboBox.setSelectedIndex(0);
		this.intermediateOperationComboBox.setRenderer(new StreamOperationClassListCellRenderer());

		final var addIntermediateOperationAction = Actions.action("addIntermediateOperation",
				e -> addIntermediateOperation());

		final var addIntermediateOperationButton = new JButton(addIntermediateOperationAction);

		this.intermediateOperationListModel = new MutableListModel<>();
		this.intermediateOperationList = new JList<>(this.intermediateOperationListModel);
		this.intermediateOperationList.setCellRenderer(new StreamOperationListCellRenderer());
		this.intermediateOperationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.intermediateOperationList.getSelectionModel().addListSelectionListener(e -> this.actions.validate());

		final var moveIntermediateOperationUpButton = new JButton(
				this.actions.add(Actions.action("moveIntermediateOperationUp", this::moveIntermediateOperationUp,
						this::canMoveIntermediateOperationUp)));
		final var moveIntermediateOperationDownButton = new JButton(
				this.actions.add(Actions.action("moveIntermediateOperationDown", this::moveIntermediateOperationDown,
						this::canMoveIntermediateOperationDown)));
		final var removeIntermediateOperationButton = new JButton(
				this.actions.add(Actions.action("removeIntermediateOperation", this::removeIntermediateOperation,
						this::isIntermediateOperationSelected)));
		final var removeAllIntermediateOperationsButton = new JButton(
				this.actions.add(Actions.action("removeAllIntermediateOperations",
						this::removeAllIntermediateOperations, this::existIntermediateOperations)));
		final var configureIntermediateOperationButton = new JButton(
				this.actions.add(Actions.action("configureIntermediateOperation", this::configureIntermediateOperation,
						this::isIntermediateOperationConfigurable)));

		panel.add(this.intermediateOperationComboBox, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(addIntermediateOperationButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(new JScrollPane(this.intermediateOperationList), new GridBagConstraints(0, 1, 1, 10, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));

		var buttonRow = 1;
		panel.add(moveIntermediateOperationUpButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(moveIntermediateOperationDownButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(removeIntermediateOperationButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(removeAllIntermediateOperationsButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(configureIntermediateOperationButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		SwingUtilities.invokeLater(() -> this.actions.validate());

		return panel;
	}

	private void addIntermediateOperation() {
		@SuppressWarnings("unchecked")
		final var selectedOperationClass = (Class<? extends IntermediateOperation<?, ?>>) this.intermediateOperationComboBox
				.getSelectedItem();

		if (selectedOperationClass != null) {
			try {
				final var operation = StreamOperation.createOperation(selectedOperationClass);

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
		if (intermediateOperation != null) {
			if (showConfigureDialog(intermediateOperation)) {
				this.intermediateOperationListModel.updateElement(intermediateOperation);
			}
		}
	}

	private Optional<IntermediateOperation<?, ?>> getSelectedIntermediateOperation() {
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
		return getSelectedIntermediateOperation().map(StreamOperation::supportsConfiguration).orElse(Boolean.FALSE);
	}

	private MutableComboBoxModel<Class<? extends TerminalOperation<?>>> terminalOperationComboBoxModel;
	private JComboBox<Class<? extends TerminalOperation<?>>> terminalOperationComboBox;
	private StreamOperationPanel terminalOperationPanel;

	private JPanel createTerminalPanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(Messages.getString("terminalPanel.title")));

		this.terminalOperationComboBoxModel = new MutableComboBoxModel<>(TerminalOperations.IMPLEMENTATIONS);
		this.terminalOperationComboBox = new JComboBox<>(this.terminalOperationComboBoxModel);
		this.terminalOperationComboBox.setSelectedIndex(0);
		this.terminalOperationComboBox.setRenderer(new StreamOperationClassListCellRenderer());
		this.terminalOperationComboBox.addActionListener(e -> terminalOperationSelected());

		this.terminalOperationPanel = new StreamOperationPanel();

		final var configureTerminalOperationButton = new JButton(
				this.actions.add(Actions.action("configureTerminalOperation", this::configureTerminalOperation,
						() -> this.currentTerminalOperation != null
								&& StreamOperation.supportsConfiguration(this.currentTerminalOperation))));

		panel.add(this.terminalOperationComboBox, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(configureTerminalOperationButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.terminalOperationPanel, new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		SwingUtilities.invokeLater(this::terminalOperationSelected);

		return panel;
	}

	private TerminalOperation<?> currentTerminalOperation;

	private void terminalOperationSelected() {
		@SuppressWarnings("unchecked")
		final var selectedTerminalOperation = (Class<? extends TerminalOperation<?>>) this.terminalOperationComboBox
				.getSelectedItem();

		if (selectedTerminalOperation != null) {
			try {
				setTerminalOperation(StreamOperation.createOperation(selectedTerminalOperation));
			} catch (final StreamOperationException ex) {
				Alerts.showError(this, ex);
			}
		}
	}

	private void setTerminalOperation(final TerminalOperation<?> terminalOperation) {
		this.currentTerminalOperation = terminalOperation;
		this.terminalOperationPanel.updateContent(this.currentTerminalOperation);
		streamSetupChanged();
		this.actions.validate();
	}

	private void configureTerminalOperation() {
		if (this.currentTerminalOperation != null) {
			if (showConfigureDialog(this.currentTerminalOperation)) {
				this.terminalOperationPanel.updateContent(this.currentTerminalOperation);
			}
		}
	}

	private <T extends StreamOperation> boolean showConfigureDialog(final T operation) {
		try {
			final var pane = StreamOperation.getConfigurationPane(operation);
			pane.setOperation(operation);
			if (ConfigurationDialog.showModal(this, pane)) {
				streamSetupChanged();
				return true;
			}
		} catch (final StreamOperationException ex) {
			Alerts.showError(this, ex);
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
		if (this.currentStreamSource != null && this.currentTerminalOperation != null) {
			final var operations = new StreamOperationSet(this.currentStreamSource,
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
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);

		final var executeButton = new JButton(
				this.actions.add(Actions.action("execute", this::execute, () -> this.executionWorker == null)));

		this.sysOutTextArea = new JTextArea();
		this.sysOutTextArea.setEditable(false);

		System.setOut(new TextAreaPrintStream(System.out, this.sysOutTextArea));

		this.logTextArea = new JTextArea();
		this.logTextArea.setEditable(false);

		log.addHandler(new TextAreaLogHandler(this.logTextArea));

		this.statisticsTableModel = new MutableTableModel<>(List.of(
				new MutableTableModel.Column<>(Messages.getString("statisticsTableModel.nameColumn"),
						StreamExecutor.ExecutionLogger::getOperationName, String.class),
				new MutableTableModel.Column<>(Messages.getString("statisticsTableModel.elementsColumn"),
						StreamExecutor.ExecutionLogger::getElementsProvided, Long.class)));
		this.statisticsTable = new JTable(this.statisticsTableModel);
		final var nameColumn = this.statisticsTable.getColumnModel().getColumn(0);
		nameColumn.setCellRenderer(new StreamOperationTableCellRenderer());

		final var tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setFocusable(false);
		addTab(tabbedPane, new JScrollPane(this.logTextArea), "tabLog.title", Images.LOG);
		addTab(tabbedPane, new JScrollPane(this.sysOutTextArea), "tabSysOut.title", Images.CONSOLE);
		addTab(tabbedPane, new JScrollPane(this.statisticsTable), "tabStatistics.title", Images.STATISTICS);

		panel.add(executeButton, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(tabbedPane, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));

		return panel;
	}

	private ExecutionWorker executionWorker;

	private void execute() {
		if (this.executionWorker == null) {
			this.logTextArea.setText("");
			this.sysOutTextArea.setText("");
			this.statisticsTableModel.removeAll();

			final var operations = new StreamOperationSet(this.currentStreamSource,
					this.intermediateOperationListModel.getAllElements(), this.currentTerminalOperation);
			final var executor = new StreamExecutor(operations);

			this.executionWorker = new ExecutionWorker(executor);
			this.executionWorker.addPropertyChangeListener(e -> {
				if (e.getSource() == this.executionWorker && e.getPropertyName().equals("state")) {
					workerStateChanged((ExecutionWorker) e.getSource(), (SwingWorker.StateValue) e.getNewValue());
				}
			});

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

			this.statisticsTableModel.replaceAll(worker.getExecutor().getExecutionLoggers());

			this.actions.validate();
			break;
		default: // ignore
		}
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
		tabbedPane.setIconAt(tabIndex, Images.getSmallIcon(iconResource));
	}

	void showAbout() {
		final var applicationName = Messages.getString("MainFrame.title");
		final var version = "1.0";
		final var jvmName = System.getProperty("java.vm.name");
		final var javaVersion = System.getProperty("java.vm.version");

		final var title = String.format(Messages.getString("about.titlePattern"), applicationName);

		final var message = String.format(Messages.getString("about.messagePattern"), applicationName, version, jvmName,
				javaVersion);

		JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
