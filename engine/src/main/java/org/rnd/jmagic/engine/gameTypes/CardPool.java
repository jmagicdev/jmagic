package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.GameType.*;

/** Represents a card pool (for example, Standard). */
public abstract class CardPool implements GameTypeRule
{
	private java.util.Collection<Class<? extends Expansion>> allowedSets;

	private java.util.Collection<String> bannedList;
	private java.util.Collection<String> restrictedList;

	public CardPool()
	{
		this(false);
	}

	public CardPool(boolean eternal)
	{
		this.allowedSets = new java.util.HashSet<Class<? extends Expansion>>();
		if(eternal)
			for(Expansion ex: Expansion.list())
				this.allowedSets.add(ex.getClass());

		this.bannedList = new java.util.LinkedList<String>();
		this.restrictedList = new java.util.LinkedList<String>();
	}

	public void allowSet(Class<? extends Expansion> e)
	{
		this.allowedSets.add(e);
	}

	public void banCard(String c)
	{
		this.bannedList.add(c);
	}

	public void restrictCard(String c)
	{
		this.restrictedList.add(c);
	}

	@Override
	public boolean checkExpansion(Expansion ex)
	{
		return this.allowedSets.contains(ex.getClass());
	}

	@Override
	public boolean checkCard(Class<? extends Card> card)
	{
		if(this.bannedList.contains(card))
			return false;
		return true;
	}

	@Override
	public boolean checkDeck(java.util.Map<String, java.util.List<String>> deck)
	{
		if(this.restrictedList.isEmpty())
			return true;

		// keys are card classes, values are numbers of that card
		java.util.Collection<String> restrictedCardsPresent = new java.util.LinkedList<String>();
		for(java.util.List<String> deckPart: deck.values())
			for(String card: deckPart)
				if(this.restrictedList.contains(card))
				{
					if(restrictedCardsPresent.contains(card))
						return false;
					restrictedCardsPresent.add(card);
				}

		return true;
	}

	@Override
	public java.util.Collection<java.util.Map<String, java.util.List<Class<? extends Card>>>> exemptDecklists()
	{
		return java.util.Collections.emptySet();
	}

	@Override
	public boolean isBaseCardPool()
	{
		return true;
	}

	@Override
	public void modifyGameState(GameState physicalState)
	{
		// Card pools don't modify the game state.
	}
}
