module name.ulbricht.streams.extended {

	// JDK dependencies
	requires java.desktop;

	// directly required project dependencies
	requires transitive name.ulbricht.streams.api;

	// provided services
	provides name.ulbricht.streams.api.StreamOperationsProvider
			with name.ulbricht.streams.extended.ExtendedOperationsProvider;

	// public available package
	exports name.ulbricht.streams.extended;
}