package name.ulbricht.streams.api;

import java.awt.Component;

public interface ConfigurationPane<T extends StreamOperation> {

	Component getComponent();

	void setOperation(final T operation);
	
	T getOperation();

	boolean apply();
}
