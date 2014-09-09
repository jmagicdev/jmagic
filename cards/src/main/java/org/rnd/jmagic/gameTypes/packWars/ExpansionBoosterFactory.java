package org.rnd.jmagic.gameTypes.packWars;

import org.rnd.jmagic.engine.*;

@Name("Expansion booster")
public class ExpansionBoosterFactory implements BoosterFactory
{
	private static int addRandomCards(java.util.Collection<Card> cards, java.util.List<String> possible, int num, GameState state, Expansion expansion)
	{
		int added = 0;
		java.util.Collections.shuffle(possible);

		for(int i = 0; (i < num) && (i < possible.size()); ++i)
		{
			String cardName = possible.remove(0);
			Class<? extends Card> cardClass = expansion.getCard(cardName);
			if(cardClass != null)
			{
				Card card = org.rnd.util.Constructor.construct(cardClass, new Class<?>[] {GameState.class}, new Object[] {state});
				cards.add(card);
				++added;
			}
		}

		return added;
	}

	private Expansion expansion;

	public ExpansionBoosterFactory()
	{
		this.expansion = null;
	}

	public ExpansionBoosterFactory(Expansion expansion)
	{
		this.expansion = expansion;
	}

	@Override
	public java.util.List<Card> createBooster(GameState state) throws org.rnd.jmagic.CardLoader.CardLoaderException
	{
		java.util.List<Card> ret = new java.util.LinkedList<Card>();

		java.util.Map<Rarity, java.util.List<String>> allCards = this.expansion.getRarityMap();

		int mythicsAdded = 0;

		// Only include a mythic in one in eight packs (this can result in 0
		// included mythics even if the probability works out if there
		// aren't any mythics in this expansion)
		if(Math.random() < 0.125)
			mythicsAdded = addRandomCards(ret, allCards.get(Rarity.MYTHIC), 1, state, this.expansion);

		// Only include a rare if a mythic wasn't included
		if(0 == mythicsAdded)
			addRandomCards(ret, allCards.get(Rarity.RARE), 1, state, this.expansion);

		addRandomCards(ret, allCards.get(Rarity.UNCOMMON), 10, state, this.expansion);
		addRandomCards(ret, allCards.get(Rarity.COMMON), 3, state, this.expansion);

		// Always include one land (not all sets have lands, so always take it
		// from the latest base set)
		java.util.List<String> lands = new java.util.LinkedList<String>();
		lands.add("Plains");
		lands.add("Island");
		lands.add("Swamp");
		lands.add("Mountain");
		lands.add("Forest");
		addRandomCards(ret, lands, 1, state, this.expansion);

		return ret;
	}

	public Expansion getExpansion()
	{
		return this.expansion;
	}

	public void setExpansion(Expansion expansion)
	{
		this.expansion = expansion;
	}
}
