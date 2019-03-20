package name.ulbricht.streams.application.ui.helper;

import java.util.List;
import java.util.function.Supplier;

import name.ulbricht.streams.api.StreamOperationSet;
import name.ulbricht.streams.application.operations.Count;
import name.ulbricht.streams.application.operations.Distinct;
import name.ulbricht.streams.application.operations.Employees;
import name.ulbricht.streams.application.operations.EmployeesDepartmentGrouping;
import name.ulbricht.streams.application.operations.EmployeesFilter;
import name.ulbricht.streams.application.operations.EmployeesSorter;
import name.ulbricht.streams.application.operations.FileLines;
import name.ulbricht.streams.application.operations.FindFiles;
import name.ulbricht.streams.application.operations.IntegerRange;
import name.ulbricht.streams.application.operations.JavaScriptFilter;
import name.ulbricht.streams.application.operations.JavaScriptFlatMap;
import name.ulbricht.streams.application.operations.JavaScriptMap;
import name.ulbricht.streams.application.operations.LowerCase;
import name.ulbricht.streams.application.operations.Modules;
import name.ulbricht.streams.application.operations.RandomIntegerGenerator;
import name.ulbricht.streams.application.operations.RegExSplitter;
import name.ulbricht.streams.application.operations.SleepPeek;
import name.ulbricht.streams.application.operations.Sorted;
import name.ulbricht.streams.application.operations.StringLengthGrouping;
import name.ulbricht.streams.application.operations.SystemOut;
import name.ulbricht.streams.application.operations.SystemOutPeek;
import name.ulbricht.streams.application.operations.SystemProperties;
import name.ulbricht.streams.application.operations.TextFileReader;
import name.ulbricht.streams.application.operations.TextFileWriter;
import name.ulbricht.streams.application.operations.TextLines;
import name.ulbricht.streams.application.operations.ToStringMapper;

public enum Preset {

	DEFAULT("Default", Preset::createDefault),

	SPLIT_WORDS("Word length statistics", Preset::createSplitWords),

	SORT_LINES("Sort lines in a file", Preset::createSortLines),

	EPLOYEES_BY_DEPARTMENT("Group employees by department", Preset::createEmployeesByDepartment),

	COUNT_LINES("Count lines in all files", Preset::createCountLines),

	GENERATE_NUMBERS("Generate a file with sorted numbers", Preset::createGenerateNumbers),

	FILTER_JAVA_SCRIPT("Filter by JavaScript", Preset::createFilterJavaScript),

	SYSTEM_PROPERTIES("Display Java system properties", Preset::createSystemProperties),

	MODULE_PACKAGES("Modules and packages", Preset::createModulesAndPackages);

	private final String displayName;
	private final Supplier<StreamOperationSet> operationsFactory;

	private Preset(final String displayName, final Supplier<StreamOperationSet> operationsFactory) {
		this.displayName = displayName;
		this.operationsFactory = operationsFactory;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public StreamOperationSet operations() {
		return this.operationsFactory.get();
	}

	private static StreamOperationSet createDefault() {
		return new StreamOperationSet(new IntegerRange(), List.of(new SleepPeek<>()), new SystemOut<>());
	}

	private static StreamOperationSet createSplitWords() {
		final var source = new TextLines();
		source.setText(
				"Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. "
						+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. "
						+ "Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
						+ "Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

		return new StreamOperationSet(source, List.of(new RegExSplitter(), new LowerCase()),
				new StringLengthGrouping());
	}

	private static StreamOperationSet createSortLines() {
		return new StreamOperationSet(new TextFileReader(), List.of(new Distinct<>(), new Sorted<>()),
				new TextFileWriter());
	}

	private static StreamOperationSet createEmployeesByDepartment() {
		return new StreamOperationSet(new Employees(), List.of(new EmployeesFilter(), new EmployeesSorter()),
				new EmployeesDepartmentGrouping());
	}

	private static StreamOperationSet createCountLines() {
		final var fileFilter = new JavaScriptFilter<>();
		fileFilter.setScript("pass = element.getFileName().toString().endsWith(\".java\")");

		return new StreamOperationSet(new FindFiles(), List.of(fileFilter, new FileLines()), new Count<>());
	}

	private static StreamOperationSet createFilterJavaScript() {
		final var filter = new JavaScriptFilter<>();
		filter.setScript("pass = element.indexOf('H') >= 0");

		return new StreamOperationSet(new TextLines(), List.of(filter), new SystemOut<>());
	}

	private static StreamOperationSet createGenerateNumbers() {
		return new StreamOperationSet(new RandomIntegerGenerator(),
				List.of(new Distinct<>(), new Sorted<>(), new ToStringMapper<>()), new TextFileWriter());
	}

	private static StreamOperationSet createSystemProperties() {
		final var propertyReader = new JavaScriptMap<>();
		propertyReader.setScript("mapped = element + \":\\t\" + java.lang.System.getProperty(element);");

		return new StreamOperationSet(new SystemProperties(), List.of(new Sorted<>(), propertyReader),
				new SystemOut<>());
	}

	private static StreamOperationSet createModulesAndPackages() {
		JavaScriptFlatMap<Module, String> packageExtractor = new JavaScriptFlatMap<>();
		packageExtractor.setScript("mappedStream = element.getPackages().stream();");

		return new StreamOperationSet(new Modules(), List.of(new SystemOutPeek<>(), packageExtractor),
				new SystemOut<>());
	}
}
