module name.ulbricht.streams.api {
	requires java.desktop;

	// public available packages
	exports name.ulbricht.streams.api;
	exports name.ulbricht.streams.api.basic;

	// provided services
	provides name.ulbricht.streams.api.StreamOperationsProvider
			with name.ulbricht.streams.api.basic.BasicStreamOperationsProvider;
}