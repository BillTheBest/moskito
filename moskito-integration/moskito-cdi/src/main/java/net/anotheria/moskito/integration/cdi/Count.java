package net.anotheria.moskito.integration.cdi;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Count qualifier using moskito.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a> , <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>, <a href="mailto:rosenberg.leon@gmail.com">Leon Rosenberg</a>
 */
@InterceptorBinding
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Count {
}
