package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;

/** Represents a restriction on the size of a sideboard. */
@Name("Required sideboard size")
@Description("The number of cards that must be in the sideboard (an empty sideboard is always permitted)")
@DependsOn(SideboardAsWishboard.class)
public class SideboardSize extends GameType.SimpleGameTypeRule
{
	private int maximumSize;

	public SideboardSize()
	{
		this.maximumSize = 0;
	}

	/**
	 * Constructs a new deck construction rule stating that the sideboard must
	 * be at maximum the specified number of cards.
	 */
	public SideboardSize(int size)
	{
		this.maximumSize = size;
	}

	@Override
	public boolean checkDeck(java.util.Map<String, java.util.List<String>> deck)
	{
		int sideboardSize = deck.get(Deck.SIDEBOARD).size();
		return sideboardSize <= this.maximumSize;
	}

	public int getSize()
	{
		return this.maximumSize;
	}

	public void setSize(int size)
	{
		this.maximumSize = size;
	}
}
