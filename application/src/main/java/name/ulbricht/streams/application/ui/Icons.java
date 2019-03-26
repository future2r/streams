package name.ulbricht.streams.application.ui;

import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class Icons {

	enum Size {
		X_SMALL(16), SMALL(24), MEDIUM(32), LARGE(48), X_LARGE(64), XX_LARGE(128), XXX_LARGE(256), XXXX_LARGE(512);

		private int dimension;

		Size(final int dimension) {
			this.dimension = dimension;
		}
	}

	static final String APPLICATION = "app";
	static final String SETUP = "setup";
	static final String CODE = "code";
	static final String EXECUTION = "execution";
	static final String SOURCE_OPERATION = "source";
	static final String INTERMEDIATE_OPERATION = "intermediate";
	static final String TERMINAL_OPERATION = "terminal";
	static final String LOG = "log";
	static final String CONSOLE = "console";
	static final String STATISTICS = "statistics";
	static final String INFO = "info";
	static final String INTERRUPT = "interrupt";

	private static final Map<String, ImageIcon> icons = new HashMap<>();

	public static Optional<Icon> getIcon(final String key, final Size size) {
		return getImageIcon(key, size).map(img -> (Icon) img);
	}

	public static List<Image> getImages(String key) {
		return Stream.of(Size.values()).map(size -> getImageIcon(key, size)).filter(Optional::isPresent)
				.map(Optional::get).map(ImageIcon::getImage).collect(Collectors.toList());
	}

	private static Optional<ImageIcon> getImageIcon(final String key, final Size size) {
		synchronized (icons) {
			return Optional.ofNullable(icons.computeIfAbsent(key + '.' + size.dimension,
					k -> loadIcon(String.format("%s-%d.png", key, size.dimension)).orElse(null)));
		}
	}

	private static Optional<ImageIcon> loadIcon(final String resourceName) {
		return Optional.ofNullable(Icons.class.getResource(resourceName)).map(ImageIcon::new);
	}

	private Icons() {
		// hidden
	}
}
