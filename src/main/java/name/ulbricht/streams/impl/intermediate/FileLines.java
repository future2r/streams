package name.ulbricht.streams.impl.intermediate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Input;
import name.ulbricht.streams.api.IntermediateOperation;
import name.ulbricht.streams.api.Name;
import name.ulbricht.streams.api.Output;

@Name("Read Text File")
@Input(Path.class)
@Output(String.class)
public final class FileLines implements IntermediateOperation<Path, String> {

	@Override
	public String getSourceCode() {
		return "stream.flatMap(f -> {\n" //
				+ "  try {\n" //
				+ "    return Files.lines(f);\n" //
				+ "  } catch (IOException ex) {\n" //
				+ "    ex.printStackTrace();\n" //
				+ "    return Stream.empty();\n" //
				+ "  }\n"//
				+ "})";
	}

	@Override
	public Stream<String> processStream(final Stream<Path> stream) {
		return stream.flatMap(f -> {
			try {
				return Files.lines(f);
			} catch (IOException ex) {
				ex.printStackTrace();
				return Stream.empty();
			}
		});
	}
}