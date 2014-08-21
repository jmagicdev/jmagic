package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PayMana extends EventType
{
	public static final EventType INSTANCE = new PayMana();

	private PayMana()
	{
		super("PAY_MANA");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// if this is the cost for a spell or ability, we really don't know
		// whether they'll be able to pay, since they have the chance to
		// play mana abilities
		if(parameters.containsKey(Parameter.OBJECT))
			return true;

		ManaPool cost = parameters.get(Parameter.COST).getOne(ManaPool.class);
		if(null == cost)
			cost = new ManaPool(parameters.get(Parameter.COST).getAll(ManaSymbol.class));
		else
		{
			// shallow copy the mana pool so we don't have to worry about
			// accidentally changing someones mana cost or something
			cost = new ManaPool(cost);
		}
		GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
		if(cause != null)
			for(ManaSymbol m: cost)
				if(m.sourceID == -1)
					m.sourceID = cause.ID;
		Player paying = parameters.get(Parameter.PLAYER).getOne(Player.class).getPhysical();
		return paying.pool.pays(game.actualState, cost);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject object = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
		ManaPool cost = new ManaPool(parameters.get(Parameter.COST).getAll(ManaSymbol.class));
		if(cost.usesX())
		{
			boolean expand = false;
			for(ManaSymbol symbol: cost)
				if(symbol.isX && !symbol.isSingular())
				{
					expand = true;
					break;
				}
			if(expand)
				cost = cost.expandX(object.getValueOfX(), object.xRestriction);
		}

		event.setResult(Empty.set);

		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
		{
			number = Sum.get(parameters.get(Parameter.NUMBER));
			if(number < 0)
				number = 1;
		}

		cost = cost.duplicate(number);
		if(object != null)
			for(ManaSymbol m: cost)
				if(m.sourceID == -1)
					m.sourceID = object.ID;

		int convertedCost = cost.converted();
		if(0 == convertedCost)
			return true;

		Player paying = parameters.get(Parameter.PLAYER).getOne(Player.class).getPhysical();

		// 608.2f If an effect gives a player the option to pay mana, he or
		// she may activate mana abilities before taking that action.
		// If this request to pay mana came from a game object, it fits the
		// description in 608.2f (above). However, if the parent of this
		// event is a PLAYER_MAY, then they've already been given the
		// opportunity to activate mana abilities.
		if(null != object && (event.parent == null || (event.parent.type != PLAYER_MAY && event.parent.type != PLAYER_MAY_PAY_X)))
			paying.mayActivateManaAbilities();

		if((paying.pool.size() < convertedCost) || !paying.pool.pays(game.actualState, cost))
			return false;

		// The player can pay the mana, so keep asking them to choose
		// until they choose a set that can pay for it
		while(true)
		{
			PlayerInterface.ChooseParameters<ManaSymbol> chooseParameters = new PlayerInterface.ChooseParameters<ManaSymbol>(convertedCost, convertedCost, new java.util.LinkedList<ManaSymbol>(paying.pool), PlayerInterface.ChoiceType.MANA_PAYMENT, PlayerInterface.ChooseReason.PAY_MANA);
			chooseParameters.replacement = cost.toString();
			ManaPool choice = new ManaPool(paying.choose(chooseParameters));
			if(choice.pays(game.actualState, cost))
			{
				paying.pool.removeAll(choice);

				// resolving spells and abilities abilities that ask you
				// to
				// pay mana aren't part of a PlayerAction being
				// performed
				if(null != game.currentAction)
					game.currentAction.manaPaid.addAll(choice);

				event.setResult(Identity.fromCollection(choice));
				return true;
			}
			// TODO: Else, let the player know that the choice was
			// invalid and they should choose again?
		}
	}
}