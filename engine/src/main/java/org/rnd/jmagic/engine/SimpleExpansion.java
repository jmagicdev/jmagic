package org.rnd.jmagic.engine;

public abstract class SimpleExpansion extends Expansion
{
	private String[] cardList;
	private String pkg;

	/**
	 * @param cardList An array of the card names in this expansion. The array
	 * must already be sorted.
	 */
	public SimpleExpansion(String[] cardList)
	{
		this(cardList, "org.rnd.jmagic.cards");
	}

	/**
	 * @param cardList An array of the card names in this expansion. The array
	 * must already be sorted.
	 * @param pkg The package containing the card classes for this set.
	 */
	public SimpleExpansion(String[] cardList, String pkg)
	{
		this.cardList = cardList;
		this.pkg = pkg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Card> getCard(String name)
	{
		if(java.util.Arrays.binarySearch(this.cardList, name) >= 0)
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
		return java.util.Arrays.asList(this.cardList);
	}

	@Override
	public java.util.List<Class<? extends Card>> getAllCardClasses()
	{
		java.util.List<Class<? extends Card>> ret = new java.util.LinkedList<Class<? extends Card>>();
		for(String name: this.cardList)
		{
			Class<? extends Card> card = this.getCard(name);
			if(null != card)
				ret.add(card);
		}
		return ret;
	}

}
