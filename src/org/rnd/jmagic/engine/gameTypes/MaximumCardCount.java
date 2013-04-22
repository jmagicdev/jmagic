package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.CardLoader.*;
import org.rnd.jmagic.engine.*;

/**
 * 100.2a. In constructed play (a way of playing in which each player creates
 * his or her own deck ahead of time), each deck must contain at least sixty
 * cards. A constructed deck may contain any number of basic land cards and no
 * more than four of any card with a particular English name other than basic
 * land cards.
 */
@Name("Same card maximum")
@Description("The maximum number of cards that can be the same")
public class MaximumCardCount extends GameType.SimpleGameTypeRule
{
	private int maximum;

	public MaximumCardCount()
	{
		this.maximum = 0;
	}

	public MaximumCardCount(int maximum)
	{
		this.maximum = maximum;
	}

	/**
	 * @return Whether the given deck conforms to the rules for constructed
	 * play.
	 * @throws CardLoaderException
	 */
	@Override
	public boolean checkDeck(java.util.Map<String, java.util.List<Class<? extends Card>>> deck)
	{
		// keys are card classes, values are numbers of that card
		java.util.Map<Class<? extends Card>, Integer> cardCounts = new java.util.HashMap<Class<? extends Card>, Integer>();
		for(java.util.List<Class<? extends Card>> deckPart: deck.values())
			for(Class<? extends Card> card: deckPart)
				if(!AnyNumberInADeck.class.isAssignableFrom(card))
				{
					Integer count = cardCounts.remove(card);
					if(count == null)
						cardCounts.put(card, 1);
					else
					{
						// It will only be >= if it is ==, since we only ever
						// increment by one, and check every time.
						if(count == this.maximum)
							return false;
						cardCounts.put(card, count + 1);
					}
				}

		return true;
	}

	public int getMaximum()
	{
		return this.maximum;
	}

	public void setMaximum(int maximum)
	{
		this.maximum = maximum;
	}
}
