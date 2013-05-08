package org.rnd.util;

/**
 * This is an alternative to {@link java.beans.ExceptionListener} but allows for
 * type-safety if the kind of exceptions are limited in type.
 */
public interface ExceptionListener<T>
{
	public void exceptionThrown(T exception);
}