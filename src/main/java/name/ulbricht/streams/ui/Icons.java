package name.ulbricht.streams.ui;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class Icons {

	static final String APPLICATION = "application";
	static final String SETUP = "setup";
	static final String CODE = "code";
	static final String EXECUTION = "execution";
	static final String SOURCE_OPERATION = "SourceOperation";
	static final String INTERMEDIATE_OPERATION = "IntermediateOperation";
	static final String TERMINAL_OPERATION = "TerminalOperation";
	static final String LOG = "log";
	static final String CONSOLE = "console";
	static final String STATISTICS = "statistics";
	static final String INFO = "info";

	private static final int[] TYPICAL_IMAGE_SIZES = new int[] { 16, 24, 32, 48, 64, 128, 256, 512 };

	private static final Map<String, ImageIcon> icons = new HashMap<>();

	static {
		loadIcons(APPLICATION, "app-%s.png");
		loadIcons(SETUP, "setup-%s.png");
		loadIcons(CODE, "code-%s.png");
		loadIcons(EXECUTION, "execution-%s.png");
		loadIcons(SOURCE_OPERATION, "source-%s.png");
		loadIcons(INTERMEDIATE_OPERATION, "intermediate-%s.png");
		loadIcons(TERMINAL_OPERATION, "terminal-%s.png");
		loadIcons(LOG, "log-%s.png");
		loadIcons(CONSOLE, "console-%s.png");
		loadIcons(STATISTICS, "statistics-%s.png");
	}

	public static Icon getSmallIcon(final String key) {
		return getIcon(key, 16);
	}

	public static Icon getIcon(final String key, final int size) {
		return (Icon) icons.get(key + '.' + size);
	}

	public static List<Icon> getIcons(final String key) {
		return IntStream.of(TYPICAL_IMAGE_SIZES).mapToObj(size -> getIcon(key, size)).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	public static List<Image> getImages(final String key) {
		return IntStream.of(TYPICAL_IMAGE_SIZES).mapToObj(size -> getIcon(key, size)).filter(Objects::nonNull)
				.map(icon -> ((ImageIcon) icon).getImage()).collect(Collectors.toList());
	}

	private static void loadIcons(final String baseKey, final String namePattern) {
		for (final var size : TYPICAL_IMAGE_SIZES) {
			final var url = Icons.class.getResource(String.format(namePattern, size));
			if (url != null) {
				try {
					final var image = ImageIO.read(url);
					icons.put(baseKey + '.' + size, new ImageIcon(image));
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private Icons() {
		// hidden
	}
}
