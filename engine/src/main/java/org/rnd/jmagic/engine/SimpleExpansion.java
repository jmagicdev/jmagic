package org.rnd.jmagic.engine;

public abstract class SimpleExpansion extends Expansion
{
	private java.util.SortedMap<String, Rarity> cardList;
	private String pkg;

	public SimpleExpansion()
	{
		this("org.rnd.jmagic.cards");
	}

	/**
	 * @param pkg The package containing the card classes for this set.
	 */
	public SimpleExpansion(String pkg)
	{
		this.cardList = new java.util.TreeMap<String, Rarity>();
		this.pkg = pkg;
	}

	protected void addCards(String... cardList)
	{
		for(String card: cardList)
			this.cardList.put(card, null);
	}

	protected void addCards(Rarity rarity, String... cardList)
	{
		for(String card: cardList)
			this.cardList.put(card, rarity);
	}

	protected void addCards(java.util.Map<String, Rarity> cardList)
	{
		this.cardList.putAll(cardList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Card> getCard(String name)
	{
		if(this.cardList.containsKey(name))
		{
			try
			{
				return (Class<? extends Card>)Class.forName(this.pkg + "." + org.rnd.jmagic.CardLoader.formatName(name));
			}
			catch(ClassNotFoundException ex)
			{
				//
			}
		}
		return null;
	}

	@Override
	public java.util.List<String> getAllCardNames()
	{
		return new java.util.LinkedList<String>(this.cardList.keySet());
	}

	@Override
	public java.util.List<Class<? extends Card>> getAllCardClasses()
	{
		java.util.List<Class<? extends Card>> ret = new java.util.LinkedList<Class<? extends Card>>();
		for(String name: this.cardList.keySet())
		{
			try
			{
				@SuppressWarnings("unchecked") Class<? extends Card> card = (Class<? extends Card>)Class.forName(this.pkg + "." + org.rnd.jmagic.CardLoader.formatName(name));
				if(null != card)
					ret.add(card);
			}
			catch(ClassNotFoundException ex)
			{
				//
			}
		}
		return ret;
	}

	@Override
	public java.util.Map<Rarity, java.util.List<String>> getRarityMap()
	{
		java.util.Map<Rarity, java.util.List<String>> ret = new java.util.EnumMap<Rarity, java.util.List<String>>(Rarity.class);

		for(Rarity rarity: Rarity.values())
			ret.put(rarity, new java.util.LinkedList<String>());

		for(java.util.Map.Entry<String, Rarity> card: this.cardList.entrySet())
			ret.get(card.getValue()).add(card.getKey());

		for(Rarity rarity: Rarity.values())
			if(ret.get(rarity).isEmpty())
				ret.remove(ret);

		return ret;
	}

	@Override
	public Rarity getRarity(String card)
	{
		return this.cardList.get(card);
	}

}
