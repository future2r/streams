package name.ulbricht.streams.impl.intermediate;

import java.util.function.Predicate;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;

@Name("Integer Compare Filter")
@Input(Integer.class)
@Output(Integer.class)
@Configuration(IntegerCompareFilterConfigurationPane.class)
public final class IntegerCompareFilter implements IntermediateOperation<Integer, Integer> {

	enum CompareOperation {
		EQUAL("=="), NOT_EQUAL("!="), LESS("<"), LESS_EQUAL("<="), GREATER_EQUAL(">="), GREATER(">");

		private final String operator;

		CompareOperation(final String operator) {
			this.operator = operator;
		}

		String operator() {
			return this.operator;
		}
	}

	private CompareOperation compareOperation = CompareOperation.EQUAL;
	private int compareValue;

	CompareOperation getCompareOperation() {
		return this.compareOperation;
	}

	void setCompareOperation(CompareOperation compareOperation) {
		this.compareOperation = compareOperation;
	}

	int getCompareValue() {
		return this.compareValue;
	}

	void setCompareValue(int compareValue) {
		this.compareValue = compareValue;
	}

	@Override
	public String getSourceCode() {
		return String.format(".filter(i -> i %s %s)", this.compareOperation.operator(),
				Integer.toString(this.compareValue));
	}

	@Override
	public String getConfigurationText() {
		return String.format("value %s %s", this.compareOperation.operator(), Integer.toString(this.compareValue));
	}

	@Override
	public Stream<Integer> processStream(final Stream<Integer> stream) {
		return stream.filter(createPredicate());
	}

	private Predicate<Integer> createPredicate() {
		switch (this.compareOperation) {
		case EQUAL:
			return i -> i == this.compareValue;
		case NOT_EQUAL:
			return i -> i != this.compareValue;
		case LESS:
			return i -> i < this.compareValue;
		case LESS_EQUAL:
			return i -> i <= this.compareValue;
		case GREATER_EQUAL:
			return i -> i >= this.compareValue;
		case GREATER:
			return i -> i > this.compareValue;
		default:
			throw new IllegalArgumentException(this.compareOperation.name());
		}
	}
}
