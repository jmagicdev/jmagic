package org.rnd.jmagic.engine;

public final class DelayedTrigger extends EventTriggeredAbility
{
	private SetGenerator duration;
	private EventFactory effect;

	// Sometimes this is different from the source of the delayed trigger. When
	// an ability makes a delayed trigger, its source is the same as the source
	// of that ability, and sometimes we want to be able to get at that ability
	// instead.
	private int causingObjectID;

	public DelayedTrigger(GameState state, String name, GameObject causingObject, java.util.Set<EventPattern> events, java.util.Set<DamagePattern> damagePatterns, java.util.Set<ZoneChangePattern> zoneChanges, EventFactory effect, SetGenerator duration)
	{
		super(state, name);

		for(EventPattern triggerEvent: events)
			this.addPattern(triggerEvent);
		for(ZoneChangePattern triggerZC: zoneChanges)
			this.addPattern(triggerZC);
		for(DamagePattern triggerDamage: damagePatterns)
			this.addPattern(triggerDamage);

		this.effect = effect;
		this.addEffect(effect);

		this.duration = duration;
		this.controllerID = causingObject.controllerID;

		if(causingObject.isActivatedAbility() || causingObject.isTriggeredAbility())
			this.sourceID = ((NonStaticAbility)causingObject).sourceID;
		else
			this.sourceID = causingObject.ID;
		this.causingObjectID = causingObject.ID;
	}

	@Override
	public DelayedTrigger create(Game game)
	{
		return new DelayedTrigger(game.physicalState, this.getName(), game.actualState.<GameObject>get(this.causingObjectID), this.eventPatterns, this.damagePatterns, this.zoneChangePatterns, this.effect, this.duration);
	}

	public boolean expired(GameState state)
	{
		return (this.duration == null ? false : !this.duration.evaluate(state, this).isEmpty());
	}

	public final GameObject getCausingObject(GameState state)
	{
		return state.get(this.causingObjectID);
	}

	@Override
	public final boolean isDelayed()
	{
		return true;
	}

	@Override
	protected boolean trigger(Event cause, GameState triggerFrom)
	{
		if(!this.game.physicalState.delayedTriggers.contains(this))
			return false;
		boolean triggered = super.trigger(cause, triggerFrom);
		if(triggered && this.duration == null)
		{
			// Remove the trigger from delayed triggers, if it's there
			this.game.physicalState.delayedTriggers.remove(this);
		}
		return triggered;
	}
}
