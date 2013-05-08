package org.rnd.util;

/**
 * Compare objects based on their String representations. If their string
 * representations are identical, compares their hash codes.
 */
public class CompareOnToString<T> implements java.util.Comparator<T>, java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(T arg0, T arg1)
	{
		int stringCompare = arg0.toString().compareTo(arg1.toString());
		if(stringCompare != 0)
			return stringCompare;
		return arg0.hashCode() - arg1.hashCode();
	}
}
