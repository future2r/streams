package name.ulbricht.streams.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

final class Messages {

	private static final String BUNDLE_NAME = "name.ulbricht.streams.ui.messages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
		// hidden
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
