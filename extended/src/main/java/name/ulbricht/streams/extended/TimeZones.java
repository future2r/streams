package name.ulbricht.streams.extended;

import java.beans.JavaBean;
import java.time.ZoneId;
import java.util.function.Supplier;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Source;

@JavaBean(description = "Creates a stream with all time zones.")
@Source
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
