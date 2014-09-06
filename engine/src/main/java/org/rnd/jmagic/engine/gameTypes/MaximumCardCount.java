package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.CardLoader.CardLoaderException;
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
	 */
	@Override
	public boolean checkDeck(java.util.Map<String, java.util.List<String>> deck)
	{
		// keys are card classes, values are numbers of that card
		java.util.Map<String, Integer> cardCounts = new java.util.HashMap<>();
		try
		{
			for(java.util.List<String> deckPart: deck.values())
				for(String card: deckPart)
					if(!AnyNumberInADeck.class.isAssignableFrom(org.rnd.jmagic.CardLoader.getCard(card)))
					{
						Integer count = cardCounts.remove(card);
						if(count == null)
							cardCounts.put(card, 1);
						else
						{
							// It will only be >= if it is ==, since we only
							// ever
							// increment by one, and check every time.
							if(count == this.maximum)
								return false;
							cardCounts.put(card, count + 1);
						}
					}
		}
		catch(CardLoaderException e)
		{
			throw new RuntimeException("Couldn't load a card during checkDeck, something is horribly broken.");
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
