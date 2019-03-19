package name.ulbricht.streams.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StreamOperation {

	StreamOperationType type();

	Class<?> input() default Object.class;

	Class<?> output() default Object.class;
}
