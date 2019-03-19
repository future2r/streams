package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.ConfigurationType.LOCAL_DATE;
import static name.ulbricht.streams.api.ConfigurationType.LONG;
import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.time.LocalDate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "LocalDate Range", type = SOURCE, output = LocalDate.class)
public final class LocalDateRange implements Supplier<Stream<LocalDate>> {

	private LocalDate start = LocalDate.now().withMonth(1).withDayOfMonth(1);
	private LocalDate end = LocalDate.now().withMonth(12).withDayOfMonth(31);
	private long increment = 1;

	@Configuration(type = LOCAL_DATE, displayName = "Start date", ordinal = 1)
	public LocalDate getStart() {
		return this.start;
	}

	public void setStart(final LocalDate start) {
		this.start = start;
	}

	@Configuration(type = LOCAL_DATE, displayName = "End date", ordinal = 2)
	public LocalDate getEnd() {
		return this.end;
	}

	public void setEnd(final LocalDate end) {
		this.end = end;
	}

	@Configuration(type = LONG, displayName = "Days to increment", ordinal = 3)
	public long getIncrement() {
		return this.increment;
	}

	public void setIncrement(final long increment) {
		this.increment = increment;
	}

	@Override
	public Stream<LocalDate> get() {
		return Stream.iterate(this.start, e -> e.isBefore(this.end.plusDays(1)), e -> e.plusDays(1));
	}

	@Override
	public String toString() {
		return String.format(
				"Stream.iterate(LocalDate.of(%1$TY, %1$Tm, %1$Td), e -> e.isBefore(LocalDate.of(%2$TY, %2$Tm, %2$Td).plusDays(1)), e -> e.plusDays(%3$d))",
				this.start, this.end, this.increment);
	}
}
