package org.rnd.util;

public class NumberNames
{
	private static final java.util.Map<Integer, String> names = new java.util.HashMap<Integer, String>();
	static
	{
		names.put(0, "zero");
		names.put(1, "one");
		names.put(2, "two");
		names.put(3, "three");
		names.put(4, "four");
		names.put(5, "five");
		names.put(6, "six");
		names.put(7, "seven");
		names.put(8, "eight");
		names.put(9, "nine");
		names.put(10, "ten");
		names.put(13, "thirteen"); // archive trap
		names.put(20, "twenty"); // jace beleren
	}

	public static String get(int number, String one)
	{
		if(number == 1)
			return one;

		if(names.containsKey(number))
			return names.get(number);

		return Integer.toString(number);
	}

	public static String getAOrAn(int number, String next)
	{
		return get(number, next.matches("[aeiouAEIOU].*") ? "an" : "a");
	}

	public static String get(int number)
	{
		return get(number, names.get(1));
	}
}
