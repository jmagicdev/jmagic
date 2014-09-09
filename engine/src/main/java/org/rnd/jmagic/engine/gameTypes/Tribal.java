package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;

/**
 * A third of the deck must share a common creature type. Also implements a
 * banned list.
 */
@Name("Tribal")
@Description("A third of the cards in the deck must share a common creature type.  Implements a banned list, as well.")
public class Tribal extends GameType.SimpleGameTypeRule
{
	private static final java.util.Collection<String> bannedList = java.util.Arrays.<String>asList("Arboria", "Balance", "Channel", "Circle of Solace", "Demonic Consultation", "Demonic Tutor", "Engineered Plague", "Flash", "Imperial Seal", "Mana Crypt", "Necropotence", "Peer Pressure", "Skullclamp", "Strip Mine", "The Abyss", "Tinker", "Tolarian Academy", "Tsabo's Decree", "Umezawa's Jitte", "Yawgmoth's Will");

	@Override
	public boolean checkCard(String card)
	{
		return !bannedList.contains(card);
	}

	@Override
	public boolean checkDeck(java.util.Map<String, java.util.List<String>> deck)
	{
		java.util.Map<SubType, Integer> count = new java.util.HashMap<SubType, Integer>();
		for(SubType subType: SubType.getAllCreatureTypes())
			count.put(subType, 0);

		int total = 0;

		try
		{
			for(java.util.List<String> cards: deck.values())
				for(String cardName: cards)
				{
					Class<? extends Card> card = org.rnd.jmagic.CardLoader.getCard(cardName);
					SubTypes subTypes = card.getAnnotation(SubTypes.class);
					if(subTypes != null)
						for(SubType subType: subTypes.value())
							if(count.containsKey(subType))
								count.put(subType, count.get(subType) + 1);
					++total;
				}
		}
		catch(org.rnd.jmagic.CardLoader.CardLoaderException e)
		{
			throw new RuntimeException("Couldn't load card: " + e.cardNames);
		}

		int max = java.util.Collections.max(count.values());

		return (max * 3) >= total;
	}
}
