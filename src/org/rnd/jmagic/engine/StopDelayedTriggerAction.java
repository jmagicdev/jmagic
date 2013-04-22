package org.rnd.jmagic.engine;

public class StopDelayedTriggerAction extends PlayerAction
{
	private final CostCollection cost;

	public StopDelayedTriggerAction(Game game, String name, CostCollection cost, int toStopID, Player who)
	{
		super(game, name, who, toStopID);
		this.cost = cost;
	}

	@Override
	public boolean perform()
	{
		if(!this.cost.manaCost.isEmpty())
		{
			Player actor = this.actor();
			Event payMana = new Event(this.game.physicalState, actor + " pays " + this.cost.manaCost, EventType.PAY_MANA);
			payMana.parameters.put(EventType.Parameter.CAUSE, org.rnd.jmagic.engine.generators.IdentifiedWithID.instance(this.sourceID));
			payMana.parameters.put(EventType.Parameter.PLAYER, org.rnd.jmagic.engine.generators.Identity.instance(actor));
			payMana.parameters.put(EventType.Parameter.COST, org.rnd.jmagic.engine.generators.Identity.instance(this.cost.manaCost));
			if(!payMana.perform(null, true))
				return false;
		}

		TriggeredAbility toStop = this.game.physicalState.get(this.sourceID);
		for(EventFactory e: this.cost.events)
		{
			Event costEvent = e.createEvent(this.game, toStop);
			if(!costEvent.perform(null, true))
				return false;
		}

		if(toStop instanceof DelayedTrigger)
			this.game.physicalState.delayedTriggers.remove(toStop);
		else
			throw new IllegalStateException("StopDelayedTriggerAction created to stop " + toStop + " which is not a delayed event triggered ability");
		return true;
	}

	@Override
	public PlayerInterface.ReversionParameters getReversionReason()
	{
		Player player = this.game.physicalState.get(this.actorID);
		TriggeredAbility toStop = this.game.physicalState.get(this.sourceID);
		return new PlayerInterface.ReversionParameters("StopDelayedTriggerAction", player.getName() + " failed to pay to stop \"" + toStop.getName() + "\".");
	}
}