package org.rnd.jmagic.gameTypes.packWars;

import org.rnd.jmagic.*;
import org.rnd.jmagic.engine.*;

@Name("Expansion booster")
public class ExpansionBoosterFactory implements BoosterFactory
{
	private static int addRandomCards(java.util.Collection<Card> cards, java.util.List<Class<? extends Card>> possible, int num, GameState state)
	{
		int added = 0;
		java.util.Collections.shuffle(possible);

		for(int i = 0; (i < num) && (i < possible.size()); ++i)
		{
			Class<? extends Card> cardClass = possible.remove(0);
			Card card = org.rnd.util.Constructor.construct(cardClass, new Class<?>[] {GameState.class}, new Object[] {state});
			cards.add(card);
			++added;
		}

		return added;
	}

	private java.util.List<Expansion> expansions;
	private boolean rarityIgnored;

	public ExpansionBoosterFactory()
	{
		this.expansions = new java.util.LinkedList<Expansion>();
		this.rarityIgnored = false;
	}

	public ExpansionBoosterFactory(Expansion expansion)
	{
		this.expansions = new java.util.LinkedList<Expansion>();
		this.expansions.add(expansion);
		this.rarityIgnored = false;
	}

	@Override
	public java.util.List<Card> createBooster(GameState state) throws org.rnd.jmagic.CardLoader.CardLoaderException
	{
		java.util.List<Card> ret = new java.util.LinkedList<Card>();

		if(this.rarityIgnored)
		{
			java.util.List<Class<? extends Card>> allCards = new java.util.LinkedList<Class<? extends Card>>();
			for(Expansion expansion: this.expansions)
				for(Class<? extends Card> cardClass: expansion.getAllCardClasses())
					allCards.add(cardClass);
			addRandomCards(ret, allCards, 14, state);
		}
		else
		{
			java.util.Map<Rarity, java.util.List<Class<? extends Card>>> allCards = new java.util.HashMap<Rarity, java.util.List<Class<? extends Card>>>();
			for(Rarity rarity: Rarity.values())
				allCards.put(rarity, new java.util.LinkedList<Class<? extends Card>>());

			for(Expansion expansion: this.expansions)
			{
				for(java.util.Map.Entry<Rarity, java.util.Set<Class<? extends Card>>> e: CardLoader.getRarityMap(expansion).entrySet())
				{
					java.util.List<Class<? extends Card>> rarityCards = allCards.get(e.getKey());
					for(Class<? extends Card> cardClass: e.getValue())
						rarityCards.add(cardClass);
				}
			}

			int mythicsAdded = 0;

			// Only include a mythic in one in eight packs (this can result in 0
			// included mythics even if the probability works out if there
			// aren't any mythics in this expansion)
			if(Math.random() < 0.125)
				mythicsAdded = addRandomCards(ret, allCards.get(Rarity.MYTHIC), 1, state);

			// Only include a rare if a mythic wasn't included
			if(0 == mythicsAdded)
				addRandomCards(ret, allCards.get(Rarity.RARE), 1, state);

			addRandomCards(ret, allCards.get(Rarity.UNCOMMON), 10, state);
			addRandomCards(ret, allCards.get(Rarity.COMMON), 3, state);
		}

		// Always include one land (not all sets have lands, so always take it
		// from the latest base set)
		java.util.List<Class<? extends Card>> lands = new java.util.LinkedList<Class<? extends Card>>();
		lands.add(CardLoader.getCard("Plains"));
		lands.add(CardLoader.getCard("Island"));
		lands.add(CardLoader.getCard("Swamp"));
		lands.add(CardLoader.getCard("Mountain"));
		lands.add(CardLoader.getCard("Forest"));
		addRandomCards(ret, lands, 1, state);

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

	public boolean isRarityIgnored()
	{
		return this.rarityIgnored;
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

	public void setRarityIgnored(boolean ignoreRarity)
	{
		this.rarityIgnored = ignoreRarity;
	}
}
