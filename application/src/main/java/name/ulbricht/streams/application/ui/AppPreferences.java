package name.ulbricht.streams.application.ui;

import java.util.logging.Level;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import name.ulbricht.streams.application.Streams;

final class AppPreferences {

	private static final Preferences userNode = Preferences.userNodeForPackage(Streams.class);;

	private static final String LOG_LEVEL = "logLevel";

	static Level getLogLevel() {
		try {
			return Level.parse(userNode.get(LOG_LEVEL, Level.FINE.getName()));
		} catch (final IllegalArgumentException ex) {
			return Level.FINE;
		}
	}

	static void setLogLevel(final Level level) {
		userNode.put(LOG_LEVEL, level.getName());
		flush();
	}

	static void flush() {
		try {
			userNode.flush();
		} catch (final BackingStoreException ex) {
			ex.printStackTrace();
		}
	}

	private AppPreferences() {
		// hidden
	}
}
