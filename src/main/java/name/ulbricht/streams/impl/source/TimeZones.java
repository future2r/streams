package name.ulbricht.streams.impl.source;

import static name.ulbricht.streams.api.StreamOperationType.SOURCE;

import java.time.ZoneId;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.StreamOperation;

@StreamOperation(name = "ZoneId List", type = SOURCE, output = ZoneId.class, description = "Creates a stream with all time zones.")
public final class TimeZones implements Supplier<Stream<ZoneId>> {

	@Override
	public Stream<ZoneId> get() {
		return ZoneId.getAvailableZoneIds().stream().map(ZoneId::of);
	}

	@Override
	public String toString() {
		return "ZoneId.getAvailableZoneIds().stream().map(ZoneId::of)";
	}
}
