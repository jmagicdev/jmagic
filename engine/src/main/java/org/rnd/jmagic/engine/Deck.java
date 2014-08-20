package org.rnd.jmagic.engine;

import org.rnd.jmagic.CardLoader.*;

public class Deck implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String MAIN_DECK = "MAIN_DECK";
	public static final String SIDEBOARD = "SIDEBOARD";

	protected final java.util.Map<String, java.util.List<String>> cards;
	public final java.util.Map<String, java.util.List<String>> publicCardMap;
	protected transient java.util.Map<String, java.util.List<Class<? extends Card>>> classMap;

	public Deck()
	{
		this.cards = new java.util.HashMap<String, java.util.List<String>>();
		this.publicCardMap = java.util.Collections.unmodifiableMap(this.cards);
		this.cards.put(MAIN_DECK, new java.util.LinkedList<String>());
		this.cards.put(SIDEBOARD, new java.util.LinkedList<String>());
		this.classMap = null;
	}

	public Deck(java.util.List<String> mainDeck, java.util.List<String> sideboard)
	{
		this();
		this.cards.get(MAIN_DECK).addAll(mainDeck);
		this.cards.get(SIDEBOARD).addAll(sideboard);
	}

	@SafeVarargs
	public Deck(Class<? extends Card>... mainDeck)
	{
		this();
		java.util.List<String> main = this.cards.get(MAIN_DECK);
		for(Class<? extends Card> cls: mainDeck)
			main.add(org.rnd.jmagic.Convenience.getName(cls));
	}

	public java.util.Map<String, java.util.List<Class<? extends Card>>> getCards() throws org.rnd.jmagic.CardLoader.CardLoaderException
	{
		if(this.classMap == null)
		{
			org.rnd.jmagic.CardLoader.CardLoaderException failAfter = null;

			java.util.Map<String, java.util.List<Class<? extends Card>>> ret = new java.util.HashMap<String, java.util.List<Class<? extends Card>>>();

			for(java.util.Map.Entry<String, java.util.List<String>> entry: this.cards.entrySet())
			{
				java.util.List<Class<? extends Card>> clsList = new java.util.LinkedList<Class<? extends Card>>();
				ret.put(entry.getKey(), clsList);

				for(String cardName: entry.getValue())
				{
					Class<? extends Card> cls = null;
					try
					{
						cls = org.rnd.jmagic.CardLoader.getCard(cardName);
					}
					catch(CardLoaderException e)
					{
						if(failAfter == null)
							failAfter = e;
						else
							failAfter.combine(e);
					}
					clsList.add(cls);
				}
			}

			if(null != failAfter)
				throw failAfter;

			this.classMap = ret;
		}

		return this.classMap;
	}
}
