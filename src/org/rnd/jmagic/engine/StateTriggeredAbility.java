package org.rnd.jmagic.engine;

/** Represents a triggered ability that triggers on a certain game state. */
public abstract class StateTriggeredAbility extends TriggeredAbility
{
	private java.util.Collection<SetGenerator> triggerConditions;

	/**
	 * Creates a triggered ability that never triggers and does nothing.
	 * 
	 * @param state The game state in which this trigger exists.
	 * @param abilityText The text of this ability.
	 */
	public StateTriggeredAbility(GameState state, String abilityText)
	{
		super(state, abilityText);

		this.triggerConditions = new java.util.LinkedList<SetGenerator>();
		this.printedVersionID = -1;
	}

	/**
	 * Adds a condition under which this ability will trigger.
	 * 
	 * @param condition A set generator representing the game state under which
	 * this ability will trigger. When this set generator evaluates to a
	 * non-empty set, the trigger condition is considered to be met.
	 */
	public void addCondition(SetGenerator condition)
	{
		this.triggerConditions.add(condition);
	}

	/** Java-copies this ability. */
	@Override
	public StateTriggeredAbility clone(GameState state)
	{
		StateTriggeredAbility ret = (StateTriggeredAbility)super.clone(state);
		ret.triggerConditions = new java.util.LinkedList<SetGenerator>(this.triggerConditions);
		return ret;
	}

	/** Triggers this ability. */
	public void trigger()
	{
		StateTriggeredAbility newAbility = (StateTriggeredAbility)super.triggerAndReturnNewInstance();
		newAbility.printedVersionID = this.ID;
	}

	/**
	 * Determines whether this ability should trigger. Intervening-if clauses at
	 * trigger time are handled by super.trigger().
	 * 
	 * @return Whether this ability should trigger.
	 */
	public boolean triggersNow()
	{
		if(!this.canTrigger())
			return false;

		for(GameObject object: this.game.actualState.stack())
			if(object instanceof StateTriggeredAbility)
				if(((StateTriggeredAbility)object).printedVersionID == this.ID)
					return false;

		Identified source = this.getSource(this.state);
		int controller;
		if(source.isGameObject())
			controller = ((GameObject)source).controllerID;
		else
			// it's a player
			controller = source.ID;
		for(GameObject object: this.game.actualState.waitingTriggers.get(controller))
			if((object instanceof StateTriggeredAbility) && (((StateTriggeredAbility)object).printedVersionID == this.ID))
				return false;

		for(SetGenerator condition: this.triggerConditions)
			if(!condition.evaluate(this.game, this).isEmpty())
				return true;

		return false;
	}
}
