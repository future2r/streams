package name.ulbricht.streams.impl.intermediate;

import name.ulbricht.streams.api.IntermediateOperation;

public final class IntermediateOperations {

	@SuppressWarnings("unchecked")
	public static final Class<? extends IntermediateOperation<?, ?>>[] IMPLEMENTATIONS = new Class[] { Parallel.class,
			Sequential.class, Distinct.class, Unordered.class, Sorted.class, SortedReverse.class, Limit.class,
			Skip.class, SystemOutPeek.class, NonNullFilter.class, StringMapper.class, StringFormatter.class,
			LowerCase.class, UpperCase.class, RegExFilter.class, RegExSplitter.class, IntegerParser.class,
			IntegerCompareFilter.class, DoubleParser.class, FileLines.class, JavaScriptFilter.class,
			JavaScriptMapper.class };

	private IntermediateOperations() {
		// hidden
	}
}
