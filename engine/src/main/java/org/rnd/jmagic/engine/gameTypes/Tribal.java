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
	public boolean checkCard(Class<? extends Card> card)
	{
		return !bannedList.contains(card.getAnnotation(Name.class).value());
	}

	@Override
	public boolean checkDeck(java.util.Map<String, java.util.List<Class<? extends Card>>> deck)
	{
		java.util.Map<SubType, Integer> count = new java.util.HashMap<SubType, Integer>();
		for(SubType subType: SubType.getAllCreatureTypes())
			count.put(subType, 0);

		int total = 0;

		for(java.util.List<Class<? extends Card>> cards: deck.values())
			for(Class<? extends Card> card: cards)
			{
				SubTypes subTypes = card.getAnnotation(SubTypes.class);
				if(subTypes != null)
					for(SubType subType: subTypes.value())
						if(count.containsKey(subType))
							count.put(subType, count.get(subType) + 1);
				++total;
			}

		int max = java.util.Collections.max(count.values());

		return (max * 3) >= total;
	}
}
