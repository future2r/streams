package name.ulbricht.streams.extended;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public final class LocalDateRangeTest {

	@Test
	public void testExecution() {
		final var operation = new LocalDateRange(LocalDate.of(1972, 1, 19), LocalDate.of(1972, 1, 31), 3);

		final var result = operation.get().toArray(LocalDate[]::new);

		assertArrayEquals(new LocalDate[] { LocalDate.of(1972, 1, 19), LocalDate.of(1972, 1, 22),
				LocalDate.of(1972, 1, 25), LocalDate.of(1972, 1, 28), LocalDate.of(1972, 1, 31) }, result);
	}

}
