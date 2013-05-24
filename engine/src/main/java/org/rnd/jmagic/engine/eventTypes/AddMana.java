package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class AddMana extends EventType
{	public static final EventType INSTANCE = new AddMana();

	 private AddMana()
	{
		super("ADD_MANA");
	}

	@Override
	public boolean addsMana()
	{
		return true;
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set result = new Set();
		Identified producer = parameters.get(Parameter.SOURCE).getOne(Identified.class);
		java.util.Set<Color> colors = parameters.get(Parameter.MANA).getAll(Color.class);
		java.util.Set<ManaSymbol.ManaType> types = parameters.get(Parameter.MANA).getAll(ManaSymbol.ManaType.class);
		ManaPool pool = new ManaPool();

		ManaSymbol addition = new ManaSymbol("");
		addition.colors.addAll(colors);
		for(ManaSymbol.ManaType type: types)
		{
			if(ManaSymbol.ManaType.COLORLESS == type)
			{
				addition.colorless = 1;
				addition.name += "1";
			}
			else
			{
				Color color = type.getColor();
				addition.colors.add(color);
				addition.name += color.getLetter();
			}
		}
		if(!addition.isZero())
			pool.add(addition);

		pool.addAll(parameters.get(Parameter.MANA).getAll(ManaSymbol.class));

		int multiply = 1;
		if(parameters.containsKey(Parameter.MULTIPLY))
			multiply = Sum.get(parameters.get(Parameter.MULTIPLY));

		if(multiply != 1)
		{
			ManaPool newPool = new ManaPool();

			if(multiply > 1)
			{
				newPool.addAll(pool);
				for(ManaSymbol symbol: pool)
					for(int i = 1; i < multiply; ++i)
						newPool.add(symbol.create());
			}

			pool = newPool;
		}

		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			number = Sum.get(parameters.get(Parameter.NUMBER));

		boolean snow = false;
		if(producer.isGameObject())
			snow = ((GameObject)producer).getSuperTypes().contains(SuperType.SNOW);
		for(Player actualPlayer: parameters.get(Parameter.PLAYER).getAll(Player.class))
		{
			Player physicalPlayer = actualPlayer.getPhysical();
			java.util.Set<CostCollection> choices = pool.explode("Add");
			// TODO : Ticket 430

			ManaPool chosen = null;
			if(choices.isEmpty()) // empty pool to start with
				chosen = pool;
			else if(choices.size() == 1)
				chosen = choices.iterator().next().manaCost;
			else
			{
				// if all of the choices are mana of a single color, present
				// it as a color choice
				boolean allSingleColorSymbols = true;
				for(CostCollection choiceCollection: choices)
				{
					ManaPool choice = choiceCollection.manaCost;
					if(choice.converted() != 1)
					{
						allSingleColorSymbols = false;
						break;
					}
					ManaSymbol symbol = choice.first();
					if(symbol.colorless != 0)
					{
						allSingleColorSymbols = false;
						break;
					}
					if(symbol.colors.size() != 1)
					{
						allSingleColorSymbols = false;
						break;
					}
				}

				if(allSingleColorSymbols)
				{
					java.util.Set<Color> colorChoices = java.util.EnumSet.noneOf(Color.class);
					for(CostCollection choice: choices)
						colorChoices.add(choice.manaCost.first().colors.iterator().next());
					Color chosenColor = physicalPlayer.chooseColor(colorChoices, producer.ID);

					ManaSymbol s = pool.first().create();
					s.colors = java.util.EnumSet.of(chosenColor);
					s.colorless = 0;
					chosen = new ManaPool(java.util.Collections.singleton(s));
				}
				else
					chosen = physicalPlayer.sanitizeAndChoose(game.actualState, 1, choices, PlayerInterface.ChoiceType.MANA_EXPLOSION, PlayerInterface.ChooseReason.HYBRID_MANA).iterator().next().manaCost;
			}

			for(int i = 0; i < number; ++i)
			{
				// Use the reference mana to create a new mana to
				// add to the pool
				for(ManaSymbol newMana: chosen)
				{
					newMana = newMana.create();
					newMana.sourceID = producer.ID;
					newMana.isSnow = snow;
					physicalPlayer.pool.add(newMana);
					result.add(newMana);
					if(game.currentAction != null)
						game.currentAction.manaProduced.add(newMana);
				}
			}
		}

		GameObject source = event.getSource();
		if(source.isActivatedAbility())
			((ActivatedAbility)source.getPhysical()).addedMana(result.getAll(ManaSymbol.class));

		event.setResult(Identity.instance(result));
		return true;
	}
}