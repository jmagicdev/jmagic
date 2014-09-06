package org.rnd.jmagic.engine;

public abstract class Expansion
{
	private static java.util.ServiceLoader<Expansion> loader = java.util.ServiceLoader.load(Expansion.class);
	private static java.util.List<Expansion> list = null;

	public static java.util.List<Expansion> list()
	{
		if(null == list)
		{
			java.util.List<Expansion> expansions = new java.util.LinkedList<Expansion>();
			for(Expansion expansion: loader)
				expansions.add(expansion);
			list = java.util.Collections.unmodifiableList(expansions);
		}
		return list;
	}

	public static String getExpansionName(Expansion x)
	{
		Name nameAnnotation = x.getClass().getAnnotation(Name.class);
		if(nameAnnotation == null)
			return null;
		return nameAnnotation.value();
	}

	public abstract Class<? extends Card> getCard(String name);

	public abstract java.util.List<String> getAllCardNames();

	public abstract java.util.List<Class<? extends Card>> getAllCardClasses();
}
