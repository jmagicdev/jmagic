package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PayManaCost extends EventType
{
	public static final EventType INSTANCE = new PayManaCost();

	private PayManaCost()
	{
		super("PAY_MANA_COST");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		if(object == null || object.getManaCost() != null)
			return true;

		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		if(null != object.alternateCosts)
			for(AlternateCost c: object.alternateCosts)
				if(c.playersMayPay.contains(player))
					return true;

		return false;
	}

	@Override
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject o = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		if(o == null)
			return;
		Player p = parameters.get(Parameter.PLAYER).getOne(Player.class);

		java.util.Collection<CostCollection> availableChoices = new java.util.LinkedList<CostCollection>();
		ManaPool totalCost = new ManaPool();
		for(ManaPool manaCost: o.getManaCost())
		{
			if(manaCost == null)
			{
				totalCost = null;
				break;
			}
			totalCost.addAll(manaCost);
		}
		if(totalCost != null)
			availableChoices.add(new CostCollection(CostCollection.TYPE_MANA, totalCost));
		if(null != o.alternateCosts)
			for(AlternateCost c: o.alternateCosts)
				if(c.playersMayPay.contains(p))
					availableChoices.add(c.cost);

		CostCollection choice = p.sanitizeAndChoose(game.actualState, 1, availableChoices, PlayerInterface.ChoiceType.ALTERNATE_COST, PlayerInterface.ChooseReason.OPTIONAL_ALTERNATE_COST).get(0);
		event.putChoices(p, java.util.Collections.singleton(choice));
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(Empty.set);

		GameObject o = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		if(o == null)
			return true;
		Player p = parameters.get(Parameter.PLAYER).getOne(Player.class);
		CostCollection cost = event.getChoices(p).getOne(CostCollection.class);

		for(EventFactory f: cost.events)
			if(!f.createEvent(game, o).perform(event, true))
				return false;
		if(!cost.manaCost.isEmpty())
		{
			java.util.Map<Parameter, Set> manaParameters = new java.util.HashMap<Parameter, Set>();
			manaParameters.put(Parameter.CAUSE, new Set(o));
			manaParameters.put(Parameter.OBJECT, new Set(o));
			manaParameters.put(Parameter.PLAYER, new Set(p));
			manaParameters.put(Parameter.COST, Set.fromCollection(cost.manaCost));
			Event payMana = createEvent(game, p + " pays " + cost.manaCost, EventType.PAY_MANA, manaParameters);
			if(!payMana.perform(event, true))
				return false;
		}

		return true;
	}
}