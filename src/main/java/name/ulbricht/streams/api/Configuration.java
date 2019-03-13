package name.ulbricht.streams.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Configuration {

	ConfigurationType type();

	String displayName() default "";

	int ordinal() default 0;
}
