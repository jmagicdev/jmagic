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

	/**
	 * @param name The name of a card
	 * @return The class literal for the card if it both was printed in this
	 * expansion, and can be found. A card that has not yet been written for
	 * jMagic may not be found.
	 */
	public abstract Class<? extends Card> getCard(String name);

	/**
	 * @return A sorted list of all the names of cards printed in this
	 * expansion.
	 */
	public abstract java.util.List<String> getAllCardNames();

	/**
	 *
	 * @return A list of all the Class literals for the cards that were printed
	 * in this expansion. It is not guaranteed that a card printed in this
	 * expansion will return a class literal, in the event that the card hasn't
	 * been written yet.
	 */
	public abstract java.util.List<Class<? extends Card>> getAllCardClasses();

	/**
	 * @return A map from Rarity to a sorted list of cards of that rarity from
	 * this expansion. Rarities without cards should not be included.
	 */
	public abstract java.util.Map<Rarity, java.util.List<String>> getRarityMap();

	/**
	 * @param card The name of a card
	 * @return The rarity of the given card in this expansion, or null if the
	 * card wasn't printed in this expansion.
	 */
	public abstract Rarity getRarity(String card);
}
