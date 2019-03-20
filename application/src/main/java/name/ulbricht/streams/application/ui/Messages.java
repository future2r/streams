package name.ulbricht.streams.application.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

final class Messages {

	private static final String BUNDLE_NAME = "name.ulbricht.streams.application.ui.messages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
		// hidden
	}

	public static String getString(final String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException ex) {
			return '!' + key + '!';
		}
	}
}
