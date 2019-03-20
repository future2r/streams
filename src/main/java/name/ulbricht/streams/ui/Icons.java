package name.ulbricht.streams.ui;

import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

	public static Icon getIcon(final String key, final Size size) {
		return getImageIcon(key, size);
	}

	public static List<Image> getImages(String key) {
		return Stream.of(Size.values()).map(size -> getImageIcon(key, size)).filter(Objects::nonNull)
				.map(ImageIcon::getImage).collect(Collectors.toList());
	}

	private static ImageIcon getImageIcon(final String key, final Size size) {
		synchronized (icons) {
			return icons.computeIfAbsent(key + '.' + size.dimension,
					k -> loadIcon(String.format("%s-%d.png", key, size.dimension)));
		}
	}

	private static ImageIcon loadIcon(final String resourceName) {
		final var url = Icons.class.getResource(resourceName);
		if (url != null)
			return new ImageIcon(url);
		return null;
	}

	private Icons() {
		// hidden
	}
}
