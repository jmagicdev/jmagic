package org.rnd.util;

public class CamelCase
{
	public static String enumValueToDisplay(String in)
	{
		in = in.toLowerCase();
		for(String wordSeperator: new String[] {" ", "_"})
		{
			StringBuilder out = new StringBuilder();
			while(in.contains(wordSeperator))
			{
				out.append(convertWord(in.substring(0, in.indexOf(wordSeperator))) + wordSeperator);
				in = in.substring(in.indexOf(wordSeperator) + 1);
			}
			out.append(convertWord(in));
			in = out.toString();
		}
		return in.replaceAll("_", "-");
	}

	public static String convertWord(String in)
	{
		String first = in.substring(0, 1);
		String rest = in.substring(1);
		return first.toUpperCase() + rest;
	}

	public static String removeNonAlpha(String in)
	{
		StringBuilder out = new StringBuilder();

		for(char c: in.toCharArray())
			if(Character.isLetter(c))
				out.append(c);

		return out.toString();
	}
}
