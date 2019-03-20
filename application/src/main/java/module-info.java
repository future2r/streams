module name.ulbricht.streams.application {
	requires java.logging;
	requires java.desktop;
	requires java.scripting;
	requires jdk.scripting.nashorn;
	requires name.ulbricht.streams.api;

	exports name.ulbricht.streams.application;
}