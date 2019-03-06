package name.ulbricht.streams.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
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
import name.ulbricht.streams.api.StreamSource;
import name.ulbricht.streams.api.TerminalOperation;
import name.ulbricht.streams.impl.intermediate.IntermediateOperations;
import name.ulbricht.streams.impl.source.StreamSources;
import name.ulbricht.streams.impl.terminal.TerminalOperations;

public final class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger("name.ulbricht.streams");

	private JTabbedPane tabbedPane;

	public MainFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setTitle(Messages.getString("MainFrame.title"));
		setIconImages(IntStream.of(16, 24, 32, 48, 64, 128, 256, 512).mapToObj(i -> "app-" + i + ".png")
				.map(getClass()::getResource).map(ImageIcon::new).map(ImageIcon::getImage)
				.collect(Collectors.toList()));

		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.add(Messages.getString("tabSetup.title"), createSetupPanel());
		this.tabbedPane.add(Messages.getString("tabCode.title"), createCodePanel());
		this.tabbedPane.add(Messages.getString("tabExecution.title"), createExecutionPanel());

		final var contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
		contentPane.add(this.tabbedPane, BorderLayout.CENTER);

		setContentPane(contentPane);
		pack();
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
	private JButton configureStreamSourceButton;

	private JPanel createSourcePanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(Messages.getString("sourcePanel.title")));

		this.streamSourceComboBoxModel = new MutableComboBoxModel<>(StreamSources.IMPLEMENTATIONS);
		this.streamSourceComboBox = new JComboBox<>(this.streamSourceComboBoxModel);
		this.streamSourceComboBox.setSelectedIndex(0);
		this.streamSourceComboBox.setRenderer(new StreamOperationClassListCellRenderer());
		this.streamSourceComboBox.addActionListener(e -> setStreamSource());

		this.streamSourcePanel = new StreamOperationPanel();

		this.configureStreamSourceButton = new JButton(Messages.getString("configureStreamSourceButton.text"));
		this.configureStreamSourceButton.addActionListener(e -> configureStreamSource());

		panel.add(this.streamSourceComboBox, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.configureStreamSourceButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.streamSourcePanel, new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		SwingUtilities.invokeLater(() -> setStreamSource());

		return panel;
	}

	private StreamSource<?> currentStreamSource;

	private void setStreamSource() {
		@SuppressWarnings("unchecked")
		final var selectedStreamSourceClass = (Class<? extends StreamSource<?>>) this.streamSourceComboBox
				.getSelectedItem();

		if (selectedStreamSourceClass != null) {
			try {
				this.currentStreamSource = StreamOperation.createOperation(selectedStreamSourceClass);

				this.streamSourcePanel.updateContent(this.currentStreamSource);
				streamSetupChanged();
			} catch (final StreamOperationException ex) {
				Alerts.showError(this, ex);
			}
		}

		this.configureStreamSourceButton.setEnabled(
				this.currentStreamSource != null && StreamOperation.supportsConfiguration(this.currentStreamSource));
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
	private JButton addIntermediateOperationButton;
	private MutableListModel<IntermediateOperation<?, ?>> intermediateOperationListModel;
	private JList<IntermediateOperation<?, ?>> intermediateOperationList;
	private JButton moveIntermediateOperationUpButton;
	private JButton moveIntermediateOperationDownButton;
	private JButton removeIntermediateOperationButton;
	private JButton removeAllIntermediateOperationsButton;
	private JButton configureIntermediateOperationButton;

	private JPanel createIntermediatePanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(Messages.getString("intermediatePanel.title")));

		this.intermediateOperationComboBoxModel = new MutableComboBoxModel<>(IntermediateOperations.IMPLEMENTATIONS);
		this.intermediateOperationComboBox = new JComboBox<>(this.intermediateOperationComboBoxModel);
		this.intermediateOperationComboBox.setSelectedIndex(0);
		this.intermediateOperationComboBox.setRenderer(new StreamOperationClassListCellRenderer());

		this.addIntermediateOperationButton = new JButton(Messages.getString("addIntermediateOperationButton.text"));
		this.addIntermediateOperationButton.addActionListener(e -> addIntermediateOperation());

		this.intermediateOperationListModel = new MutableListModel<>();
		this.intermediateOperationList = new JList<>(this.intermediateOperationListModel);
		this.intermediateOperationList.setCellRenderer(new StreamOperationListCellRenderer());
		this.intermediateOperationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.intermediateOperationList.getSelectionModel()
				.addListSelectionListener(e -> updateIntermediateOperationsButtons());

		this.moveIntermediateOperationUpButton = new JButton(
				Messages.getString("moveIntermediateOperationUpButton.text"));
		this.moveIntermediateOperationUpButton.addActionListener(e -> moveIntermediateOperationUp());
		this.moveIntermediateOperationDownButton = new JButton(
				Messages.getString("moveIntermediateOperationDownButton.text"));
		this.moveIntermediateOperationDownButton.addActionListener(e -> moveIntermediateOperationDown());

		this.removeIntermediateOperationButton = new JButton(
				Messages.getString("removeIntermediateOperationButton.text"));
		this.removeIntermediateOperationButton.addActionListener(e -> removeIntermediateOperation());

		this.removeAllIntermediateOperationsButton = new JButton(
				Messages.getString("removeAllIntermediateOperationsButton.text"));
		this.removeAllIntermediateOperationsButton.addActionListener(e -> removeAllIntermediateOperations());

		this.configureIntermediateOperationButton = new JButton(
				Messages.getString("configureIntermediateOperationButton.text"));
		this.configureIntermediateOperationButton.addActionListener(e -> configureIntermediateOperation());

		panel.add(this.intermediateOperationComboBox, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.addIntermediateOperationButton, new GridBagConstraints(1, 0, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(new JScrollPane(this.intermediateOperationList), new GridBagConstraints(0, 1, 1, 10, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));

		var buttonRow = 1;
		panel.add(this.moveIntermediateOperationUpButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.moveIntermediateOperationDownButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.removeIntermediateOperationButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.removeAllIntermediateOperationsButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.configureIntermediateOperationButton, new GridBagConstraints(1, buttonRow++, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		SwingUtilities.invokeLater(() -> updateIntermediateOperationsButtons());

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

		updateIntermediateOperationsButtons();
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
			updateIntermediateOperationsButtons();
			streamSetupChanged();
		}
	}

	private void removeAllIntermediateOperations() {
		if (this.intermediateOperationListModel.getSize() > 0) {
			this.intermediateOperationListModel.removeAllElements();
			updateIntermediateOperationsButtons();
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

	private void updateIntermediateOperationsButtons() {
		final var size = this.intermediateOperationListModel.getSize();
		final var selectedIndex = this.intermediateOperationList.getSelectedIndex();

		this.moveIntermediateOperationUpButton.setEnabled(selectedIndex > 0);
		this.moveIntermediateOperationDownButton.setEnabled(selectedIndex >= 0 && selectedIndex < (size - 1));
		this.removeIntermediateOperationButton.setEnabled(selectedIndex >= 0);
		this.removeAllIntermediateOperationsButton.setEnabled(size > 0);

		final var selectedOperation = this.intermediateOperationList.getSelectedValue();
		this.configureIntermediateOperationButton
				.setEnabled(selectedOperation != null && StreamOperation.supportsConfiguration(selectedOperation));
	}

	private MutableComboBoxModel<Class<? extends TerminalOperation<?>>> terminalOperationComboBoxModel;
	private JComboBox<Class<? extends TerminalOperation<?>>> terminalOperationComboBox;
	private StreamOperationPanel terminalOperationPanel;
	private JButton configureTerminalOperationButton;

	private JPanel createTerminalPanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(Messages.getString("terminalPanel.title")));

		this.terminalOperationComboBoxModel = new MutableComboBoxModel<>(TerminalOperations.IMPLEMENTATIONS);
		this.terminalOperationComboBox = new JComboBox<>(this.terminalOperationComboBoxModel);
		this.terminalOperationComboBox.setSelectedIndex(0);
		this.terminalOperationComboBox.setRenderer(new StreamOperationClassListCellRenderer());
		this.terminalOperationComboBox.addActionListener(e -> setTerminalOperation());

		this.terminalOperationPanel = new StreamOperationPanel();

		this.configureTerminalOperationButton = new JButton(
				Messages.getString("configureTerminalOperationButton.text"));
		this.configureTerminalOperationButton.addActionListener(e -> configureTerminalOperation());

		panel.add(this.terminalOperationComboBox, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.configureTerminalOperationButton, new GridBagConstraints(1, 0, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.terminalOperationPanel, new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));

		SwingUtilities.invokeLater(() -> setTerminalOperation());

		return panel;
	}

	private TerminalOperation<?> currentTerminalOperation;

	private void setTerminalOperation() {
		@SuppressWarnings("unchecked")
		final var selectedTerminalOperation = (Class<? extends TerminalOperation<?>>) this.terminalOperationComboBox
				.getSelectedItem();

		if (selectedTerminalOperation != null) {
			try {
				this.currentTerminalOperation = StreamOperation.createOperation(selectedTerminalOperation);

				this.terminalOperationPanel.updateContent(this.currentTerminalOperation);
				streamSetupChanged();
			} catch (final StreamOperationException ex) {
				Alerts.showError(this, ex);
			}
		}

		this.configureTerminalOperationButton.setEnabled(this.currentTerminalOperation != null
				&& StreamOperation.supportsConfiguration(this.currentTerminalOperation));
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
	private JButton copyCodeButton;

	private JPanel createCodePanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);

		this.codeTextArea = new JTextArea();
		this.codeTextArea.setEditable(false);

		this.copyCodeButton = new JButton(Messages.getString("copyCodeButton.text"));
		this.copyCodeButton.addActionListener(e -> copyCode());

		panel.add(new JScrollPane(this.codeTextArea), new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.copyCodeButton, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));

		return panel;
	}

	private void copyCode() {
		this.codeTextArea.selectAll();
		this.codeTextArea.copy();
	}

	private void updateSourceCode() {
		if (this.currentStreamSource != null && this.currentTerminalOperation != null) {
			SourceCodeBuilder builder = new SourceCodeBuilder(this.currentStreamSource,
					this.intermediateOperationListModel.getAllElements(), this.currentTerminalOperation);
			this.codeTextArea.setText(builder.getSourceCode());
		}
	}

	private JButton executeButton;
	private JTabbedPane executionTabbedPane;
	private JTextArea sysOutTextArea;
	private JTextArea logTextArea;
	private ExecutionLoggerTableModel statisticsTableModel;
	private JTable statisticsTable;

	private JPanel createExecutionPanel() {
		final var panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);

		this.executeButton = new JButton(Messages.getString("executeButton.text"));
		this.executeButton.addActionListener(e -> execute());

		this.sysOutTextArea = new JTextArea();
		this.sysOutTextArea.setEditable(false);

		System.setOut(new TextAreaPrintStream(System.out, this.sysOutTextArea));

		this.logTextArea = new JTextArea();
		this.logTextArea.setEditable(false);

		log.addHandler(new TextAreaLogHandler(this.logTextArea));

		this.statisticsTableModel = new ExecutionLoggerTableModel();
		this.statisticsTable = new JTable(this.statisticsTableModel);

		this.executionTabbedPane = new JTabbedPane();
		this.executionTabbedPane.add(Messages.getString("tabLog.title"), new JScrollPane(this.logTextArea));
		this.executionTabbedPane.add(Messages.getString("tabSysOut.title"), new JScrollPane(this.sysOutTextArea));
		this.executionTabbedPane.add(Messages.getString("tabStatistics.title"), new JScrollPane(this.statisticsTable));

		panel.add(this.executeButton, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		panel.add(this.executionTabbedPane, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));

		return panel;
	}

	private ExecutionWorker executionWorker;

	private void execute() {
		if (this.executionWorker == null) {
			this.logTextArea.setText("");
			this.sysOutTextArea.setText("");
			this.statisticsTableModel.removeAll();

			final var executor = new StreamExecutor(this.currentStreamSource,
					this.intermediateOperationListModel.getAllElements(), this.currentTerminalOperation);

			this.executionWorker = new ExecutionWorker(executor);
			this.executionWorker.addPropertyChangeListener(e -> {
				if (e.getSource() == this.executionWorker && e.getPropertyName().equals("state")) {
					workerStateChanged((ExecutionWorker) e.getSource(), (SwingWorker.StateValue) e.getNewValue());
				}
			});

			this.executeButton.setEnabled(false);

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
			
			this.executeButton.setEnabled(true);
			break;
		default: // ignore
		}
	}

	private static String toString(final Object result) {
		if (result != null && result.getClass().isArray())
			return Arrays.toString((Object[]) result);
		return Objects.toString(result);
	}
}
