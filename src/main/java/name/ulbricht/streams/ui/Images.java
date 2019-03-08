package name.ulbricht.streams.ui;

import java.awt.Image;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.MultiResolutionImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class Images extends ListResourceBundle {

	private static final String BUNDLE_NAME = "name.ulbricht.streams.ui.Images";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	static final String APPLICATION = "application";
	static final String SETUP = "setup";
	static final String CODE = "code";
	static final String EXECUTION = "execution";
	static final String STREAM_SOURCE = "StreamSource";
	static final String INTERMEDIATE_OPERATION = "IntermediateOperation";
	static final String TERMINAL_OPERATION = "TerminalOperation";
	static final String LOG = "log";
	static final String CONSOLE = "console";
	static final String STATISTICS = "statistics";

	public static Icon getSmallIcon(final String key) {
		return getIcon(key, 16);
	}

	public static Icon getIcon(final String key, final int size) {
		return getImage(key, size).map(ImageIcon::new).orElse(null);
	}

	public static Optional<Image> getImage(final String key, final int size) {
		try {
			return Optional
					.of(((MultiResolutionImage) RESOURCE_BUNDLE.getObject(key)).getResolutionVariant(size, size));
		} catch (final MissingResourceException ex) {
			return Optional.empty();
		}
	}

	public static List<Icon> getIcons(final String key) {
		return getImages(key).stream().map(ImageIcon::new).collect(Collectors.toList());
	}

	public static List<Image> getImages(final String key) {
		try {
			return ((MultiResolutionImage) RESOURCE_BUNDLE.getObject(key)).getResolutionVariants();
		} catch (final MissingResourceException ex) {
			return List.of();
		}
	}

	@Override
	protected Object[][] getContents() {
		return new Object[][] { //
				{ APPLICATION, loadMultiResolutionImage("app-%s.png") }, //
				{ SETUP, loadMultiResolutionImage("setup-%s.png") }, //
				{ CODE, loadMultiResolutionImage("code-%s.png") }, //
				{ EXECUTION, loadMultiResolutionImage("execution-%s.png") }, //
				{ STREAM_SOURCE, loadMultiResolutionImage("source-%s.png") }, //
				{ INTERMEDIATE_OPERATION, loadMultiResolutionImage("intermediate-%s.png") }, //
				{ TERMINAL_OPERATION, loadMultiResolutionImage("terminal-%s.png") }, //
				{ LOG, loadMultiResolutionImage("log-%s.png") }, //
				{ CONSOLE, loadMultiResolutionImage("console-%s.png") }, //
				{ STATISTICS, loadMultiResolutionImage("statistics-%s.png") } //
		};
	}

	private static final int[] TYPICAL_IMAGE_SIZES = new int[] { 16, 24, 32, 48, 64, 128, 256, 512 };

	private static MultiResolutionImage loadMultiResolutionImage(final String namePattern) {
		return new BaseMultiResolutionImage(IntStream.of(TYPICAL_IMAGE_SIZES)
				.mapToObj(size -> String.format(namePattern, size)).map(Images.class::getResource)
				.filter(Objects::nonNull).map(Images::readImage).filter(Objects::nonNull).toArray(Image[]::new));
	}

	private static Image readImage(final URL url) {
		try {
			return ImageIO.read(url);
		} catch (final IOException ex) {
			return null;
		}
	}
}
