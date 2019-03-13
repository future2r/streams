package name.ulbricht.streams.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Configurations.class)
public @interface Configuration {

	String name();

	ConfigurationType type();

	String displayName() default "";
}
