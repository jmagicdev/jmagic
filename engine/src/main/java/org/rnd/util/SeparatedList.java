package org.rnd.util;

public class SeparatedList
{
	public static StringBuilder get(String separator, String lastItem, Object... items)
	{
		return get(separator, lastItem, java.util.Arrays.asList(items));
	}

	/**
	 * Returns a comma separated list of the specified items.
	 * 
	 * @param separator The list separator. E.g., "X, Y, and Z" -- this would be
	 * ", ". Note the space.
	 * @param lastItem The word to use immediately before the last item. E.g.,
	 * "X, Y, and Z" -- this would be "and".
	 * @param items The items to list.
	 * @return The list.
	 */
	public static StringBuilder get(String separator, String lastItem, java.util.Collection<?> items)
	{
		if(items.isEmpty())
			return new StringBuilder("nothing");
		if(items.size() == 1)
			return new StringBuilder(items.iterator().next().toString());
		java.util.Iterator<?> it = items.iterator();
		if(items.size() == 2)
		{
			StringBuilder ret = new StringBuilder(it.next().toString());
			ret.append(0 == lastItem.length() ? separator : (separator + lastItem + " "));
			ret.append(it.next());
			return ret;
		}

		StringBuilder ret = new StringBuilder();
		for(int i = 0; i < items.size() - 1; i++)
			ret.append(it.next()).append(separator);
		if(0 != lastItem.length())
			ret.append(lastItem).append(" ");
		ret.append(it.next());
		return ret;
	}

	/** cover for get(", ", lastItem, items) */
	public static StringBuilder get(String lastItem, java.util.Collection<?> items)
	{
		return get(", ", lastItem, items);
	}
}
