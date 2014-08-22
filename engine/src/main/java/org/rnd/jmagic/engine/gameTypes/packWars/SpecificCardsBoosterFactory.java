package org.rnd.jmagic.engine.gameTypes.packWars;

import org.rnd.jmagic.CardLoader.*;
import org.rnd.jmagic.engine.*;

@Name("Specific cards")
public class SpecificCardsBoosterFactory implements BoosterFactory
{
	private java.util.List<String> cardNames;

	public SpecificCardsBoosterFactory()
	{
		this.cardNames = new java.util.ArrayList<String>();
	}

	public SpecificCardsBoosterFactory(String... cardNames)
	{
		this();
		this.setCardNames(cardNames);
	}

	public String[] getCardNames()
	{
		return this.cardNames.toArray(new String[this.cardNames.size()]);
	}

	public String getCardNames(int index)
	{
		return this.cardNames.get(index);
	}

	public void setCardNames(String[] cardNames)
	{
		this.cardNames = java.util.Arrays.asList(cardNames);
	}

	public void setCardNames(int index, String cardName)
	{
		while(index >= this.cardNames.size())
			this.cardNames.add(null);
		this.cardNames.set(index, cardName);
	}

	@Override
	public java.util.List<Card> createBooster(GameState state) throws CardLoaderException
	{
		java.util.List<Card> ret = new java.util.LinkedList<Card>();

		for(String name: this.cardNames)
		{
			Class<? extends Card> clazz = org.rnd.jmagic.CardLoader.getCard(name);
			Card instance = org.rnd.util.Constructor.construct((Class<? extends Card>)clazz, new Class<?>[] {GameState.class}, new Object[] {state});
			ret.add(instance);
		}

		return ret;
	}

}
