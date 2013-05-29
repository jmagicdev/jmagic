package org.rnd.jmagic.engine.gameTypes.packWars;

import org.rnd.jmagic.*;
import org.rnd.jmagic.engine.*;

@Name("Full expansion")
public class FullExpansionBoosterFactory implements BoosterFactory
{
	private java.util.List<Expansion> expansions;

	public FullExpansionBoosterFactory()
	{
		this.expansions = new java.util.LinkedList<Expansion>();
	}

	public FullExpansionBoosterFactory(Expansion expansion)
	{
		this.expansions = new java.util.LinkedList<Expansion>();
		this.expansions.add(expansion);
	}

	@Override
	public java.util.List<Card> createBooster(GameState state) throws org.rnd.jmagic.CardLoader.CardLoaderException
	{
		java.util.List<Card> ret = new java.util.LinkedList<Card>();

		for(Expansion expansion: this.expansions)
			for(Class<? extends Card> cardClass: CardLoader.getCards(java.util.Arrays.asList(expansion)))
			{
				Card card = org.rnd.util.Constructor.construct(cardClass, new Class<?>[] {GameState.class}, new Object[] {state});
				card.setExpansionSymbol(expansion);
				ret.add(card);
			}

		return ret;
	}

	public Expansion[] getExpansions()
	{
		return this.expansions.toArray(new Expansion[this.expansions.size()]);
	}

	public Expansion getExpansions(int index)
	{
		return this.expansions.get(index);
	}

	public void setExpansions(Expansion[] expansions)
	{
		this.expansions = java.util.Arrays.asList(expansions);
	}

	public void setExpansions(int index, Expansion expansion)
	{
		while(this.expansions.size() <= index)
			this.expansions.add(null);
		this.expansions.set(index, expansion);
	}
}
