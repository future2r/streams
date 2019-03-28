module name.ulbricht.streams.application {
	
	// JDK dependencies
	requires java.logging;
	requires java.desktop;
	requires java.prefs;

	// directly required project dependencies
	// other modules may be dynamically discovered at runtime
	requires name.ulbricht.streams.api;

	// used services
	uses name.ulbricht.streams.api.StreamOperationsProvider;

	// public available package
	exports name.ulbricht.streams.application;
}