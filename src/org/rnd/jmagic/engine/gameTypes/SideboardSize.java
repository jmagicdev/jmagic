package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;

/** Represents a restriction on the size of a sideboard. */
@Name("Required sideboard size")
@Description("The number of cards that must be in the sideboard (an empty sideboard is always permitted)")
@DependsOn(SideboardAsWishboard.class)
public class SideboardSize extends GameType.SimpleGameTypeRule
{
	private int size;

	public SideboardSize()
	{
		this.size = 0;
	}

	/**
	 * Constructs a new deck construction rule stating that the sideboard must
	 * either be zero cards or the specified number of cards.
	 */
	public SideboardSize(int size)
	{
		this.size = size;
	}

	@Override
	public boolean checkDeck(java.util.Map<String, java.util.List<Class<? extends Card>>> deck)
	{
		int sideboardSize = deck.get(Deck.SIDEBOARD).size();
		return sideboardSize == 0 || sideboardSize == this.size;
	}

	public int getSize()
	{
		return this.size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}
}
