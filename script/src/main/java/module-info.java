module name.ulbricht.streams.script {

	// JDK dependencies
	requires java.desktop;
	requires java.scripting;
	requires jdk.scripting.nashorn;

	// directly required project dependencies
	requires transitive name.ulbricht.streams.api;
	requires name.ulbricht.streams.operations;

	// public available package
	exports name.ulbricht.streams.script;

	// provided services
	provides name.ulbricht.streams.api.StreamOperationsProvider
			with name.ulbricht.streams.script.ScriptOperationsProvider;
}