package name.ulbricht.streams.application.operations;

import java.beans.BeanProperty;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.JavaBean;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import java.util.stream.Stream;

import name.ulbricht.streams.api.Intermediate;

@JavaBean(description = "Maps an object to the value of a property.")
@Intermediate
public final class PropertyMapper implements Function<Stream<Object>, Stream<Object>> {

	private String property = "class";

	@BeanProperty(description = "Programatic name of the property")
	public String getProperty() {
		return this.property;
	}

	public void setProperty(final String property) {
		this.property = property;
	}

	@Override
	public Stream<Object> apply(final Stream<Object> stream) {
		return stream.map(obj -> {
			try {
				return Stream.of(Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors())
						.filter(pd -> pd.getName().equals(this.property)) //
						.findFirst() //
						.map(PropertyDescriptor::getReadMethod) //
						.map(rm -> {
							try {
								return rm.invoke(obj, (Object[]) null);
							} catch (final IllegalAccessException | InvocationTargetException ex) {
								return null;
							}
						}) //
						.orElse(null);
			} catch (final IntrospectionException ex) {
				return null;
			}
		});
	}

	@Override
	public String toString() {
		return String.format(".map(obj -> { try {\n"
				+ "    return Stream.of(Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors())\n"
				+ "      .filter(pd -> pd.getName().equals(\"%s\")).findFirst()\n"
				+ "      .map(PropertyDescriptor::getReadMethod)\n"
				+ "      .map(rm -> { try { return rm.invoke(obj, (Object[]) null);\n"
				+ "        } catch (IllegalAccessException | InvocationTargetException e) { return null; }\n"
				+ "       }).orElse(null);\n" + "  } catch (IntrospectionException e) { return null; }\n" + "})",
				this.property);
	}
}
