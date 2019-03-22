module name.ulbricht.streams.operations {

	// JDK dependencies
	requires java.logging;
	requires java.desktop;

	// directly required project dependencies
	requires transitive name.ulbricht.streams.api;

	// provided services
	provides name.ulbricht.streams.api.StreamOperationsProvider
			with name.ulbricht.streams.operations.OperationsProvider;

	// public available package
	exports name.ulbricht.streams.operations;
}