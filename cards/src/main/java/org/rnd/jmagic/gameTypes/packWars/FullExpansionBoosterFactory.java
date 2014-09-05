package org.rnd.jmagic.gameTypes.packWars;

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
			for(Class<? extends Card> cardClass: expansion.getAllCardClasses())
				ret.add(org.rnd.util.Constructor.construct(cardClass, new Class<?>[] {GameState.class}, new Object[] {state}));

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
