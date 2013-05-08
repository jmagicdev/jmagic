package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

/**
 * A third of the deck must share a common creature type. Also implements a
 * banned list.
 */
@Name("Tribal")
@Description("A third of the cards in the deck must share a common creature type.  Implements a banned list, as well.")
public class Tribal extends GameType.SimpleGameTypeRule
{
	private static final java.util.Collection<Class<?>> bannedList = java.util.Arrays.<Class<?>>asList(
	// Arboria.class,
	// Balance.class,
	// Channel.class,
	// CircleofSolace.class,
	DemonicConsultation.class, DemonicTutor.class, EngineeredPlague.class,
	// Flash.class,
	ImperialSeal.class, ManaCrypt.class,
	// Necropotence.class,
	// PeerPressure.class,
	Skullclamp.class, StripMine.class,
	// TheAbyss.class,
	Tinker.class, TolarianAcademy.class,
	// TsabosDecree.class,
	UmezawasJitte.class, YawgmothsWill.class);

	@Override
	public boolean checkCard(Class<? extends Card> card)
	{
		return !bannedList.contains(card);
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
