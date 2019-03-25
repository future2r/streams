module name.ulbricht.streams.entity {

	// JDK dependencies
	requires java.desktop;

	// directly required project dependencies
	requires transitive name.ulbricht.streams.api;

	// public available package
	exports name.ulbricht.streams.entity;

	// provided services
	provides name.ulbricht.streams.api.StreamOperationsProvider
			with name.ulbricht.streams.entity.EntityOperationsProvider;
}