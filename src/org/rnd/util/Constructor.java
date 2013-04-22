package org.rnd.util;

/**
 * A convenience class to hold the convenient method Constructor.construct().
 */
public class Constructor
{
	/**
	 * Construct an object reflectively without having to catch all those pesky
	 * exceptions.
	 * 
	 * @param toConstruct The type of object to construct
	 * @param parameterTypes The types of the parameters for the desired
	 * constructor
	 * @param parameters The parameters to that constructor
	 * @return A brand new object of the type asked for
	 */
	public static <T> T construct(Class<T> toConstruct, Class<?>[] parameterTypes, Object[] parameters)
	{
		try
		{
			return toConstruct.getConstructor(parameterTypes).newInstance(parameters);
		}
		catch(IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch(InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch(java.lang.reflect.InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
		catch(NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
	}
}
