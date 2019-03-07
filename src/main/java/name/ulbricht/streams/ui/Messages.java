package name.ulbricht.streams.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

final class Messages {

	private static final String BUNDLE_NAME = "name.ulbricht.streams.ui.messages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
		// hidden
	}

	public static String getString(final String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static Icon getIcon(final String key) {
		try {
			final var iconName = RESOURCE_BUNDLE.getString(key);
			return new ImageIcon(Messages.class.getResource(iconName));
		} catch (final MissingResourceException e) {
			return null;
		}
	}
}
