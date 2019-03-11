package name.ulbricht.streams.impl.source;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Configuration;
import name.ulbricht.streams.api.ConfigurationType;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;
import name.ulbricht.streams.api.StreamSource;

@Name("Integer Range")
@Output(Integer.class)
@Configuration(name = "start", type = ConfigurationType.INTEGER, displayName = "Start")
@Configuration(name = "end", type = ConfigurationType.INTEGER, displayName = "End")
@Configuration(name = "closed", type = ConfigurationType.BOOLEAN, displayName = "Range is closed")
public final class IntegerRange implements StreamSource<Integer> {

	private int start = 0;
	private int end = 100;
	private boolean closed = false;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@Override
	public String getSourceCode() {
		return String.format("IntStream.range%s(%s, %s).boxed()", this.closed ? "Closed" : "",
				Integer.toString(this.start), Integer.toString(this.end));
	}

	@Override
	public String getConfigurationText() {
		return String.format("start=%s, end=%s, closed=%s", Integer.toString(this.start), Integer.toString(this.end),
				Boolean.toString(this.closed));
	}

	@Override
	public Stream<Integer> createStream() {
		final IntStream stream;
		if (this.closed)
			stream = IntStream.rangeClosed(this.start, this.end);
		else
			stream = IntStream.range(this.start, this.end);
		return stream.boxed();
	}
}
