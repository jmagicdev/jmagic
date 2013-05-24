package org.rnd.jmagic.engine;

public abstract class EventTriggeredAbility extends TriggeredAbility
{
	public DamageAssignment.Batch damageCause;
	public int eventCause;
	public ZoneChange zoneChangeCause;

	protected java.util.Set<EventPattern> eventPatterns;
	protected java.util.Set<ZoneChangePattern> zoneChangePatterns;
	protected java.util.Set<DamagePattern> damagePatterns;

	protected EventTriggeredAbility(GameState state, String abilityText)
	{
		super(state, abilityText);

		this.eventCause = 0;
		this.damageCause = null;
		this.zoneChangeCause = null;

		this.eventPatterns = new java.util.HashSet<EventPattern>();
		this.zoneChangePatterns = new java.util.HashSet<ZoneChangePattern>();
		this.damagePatterns = new java.util.HashSet<DamagePattern>();
	}

	public void addPattern(EventPattern pattern)
	{
		this.eventPatterns.add(pattern);
	}

	public void addPattern(ZoneChangePattern pattern)
	{
		this.zoneChangePatterns.add(pattern);
	}

	public void addPattern(DamagePattern pattern)
	{
		this.damagePatterns.add(pattern);
	}

	/** Java-copies this ability. */
	@Override
	public EventTriggeredAbility clone(GameState state)
	{
		EventTriggeredAbility ret = (EventTriggeredAbility)super.clone(state);

		ret.eventPatterns = new java.util.HashSet<EventPattern>();
		for(EventPattern e: this.eventPatterns)
			ret.eventPatterns.add(e);

		ret.zoneChangePatterns = new java.util.HashSet<ZoneChangePattern>();
		for(ZoneChangePattern e: this.zoneChangePatterns)
			ret.zoneChangePatterns.add(e);

		return ret;
	}

	@Override
	public boolean isManaAbility()
	{
		for(EventPattern pattern: this.eventPatterns)
			if(pattern.matchesManaAbilities())
				return !this.hasTargets() && this.addsMana();
		return false;
	}

	/**
	 * Determines whether this ability triggers on the given event in the
	 * specified state. Used for look-back-in-time triggers.
	 * 
	 * @param event The event to check against.
	 * @param state The game state in which to check.
	 * @return True if this ability triggered, false otherwise.
	 */
	public final boolean triggerOn(Event event, GameState state, boolean previousState)
	{
		if(!this.canTrigger(state))
			return false;

		boolean ret = false;

		for(EventPattern triggerEvent: this.eventPatterns)
		{
			// If we're checking the current state, only check triggers that
			// don't look back. For the previous state, only check triggers
			// that do.
			if((!previousState && !triggerEvent.looksBackInTime()) || (previousState && triggerEvent.looksBackInTime()))
				if(triggerEvent.match(event, this, state))
					if(this.trigger(event, state))
						ret = true;
		}

		if(event.isTopLevel())
		{
			for(ZoneChangePattern pattern: this.zoneChangePatterns)
				if(previousState == pattern.looksBackInTime())
					for(ZoneChange change: event.getZoneChanges())
						if(pattern.match(change, this, state) && !this.isStopped(change, state))
							if(this.trigger(change, state))
								ret = true;

			// Damage patterns never look back in time
			if(!this.damagePatterns.isEmpty() && !previousState)
			{
				DamageAssignment.Batch newBatch = new DamageAssignment.Batch(event.getDamage());
				if(!newBatch.isEmpty())
					for(DamagePattern pattern: this.damagePatterns)
						for(DamageAssignment.Batch triggeredBatch: pattern.match(newBatch, this, state))
							if(this.trigger(triggeredBatch, state))
								ret = true;
			}
		}

		if(ret)
			for(java.util.Collection<? extends TriggeredAbility> c: this.game.physicalState.waitingTriggers.values())
				for(TriggeredAbility t: c)
					if(t.printedVersionID == this.ID)
						return false;

		return ret;
	}

	/**
	 * Triggers this ability.
	 * 
	 * @param cause The damage batch that caused this ability to trigger.
	 * @param triggerFrom The game state in which this ability triggered.
	 * @return Whether the ability successfully triggered.
	 */
	protected boolean trigger(DamageAssignment.Batch cause, GameState triggerFrom)
	{
		EventTriggeredAbility triggered = (EventTriggeredAbility)this.triggerAndReturnNewInstance(triggerFrom);

		// If the trigger triggers successfully
		if(null != triggered)
		{
			triggered.damageCause = cause;
			return true;
		}

		return false;
	}

	/**
	 * Triggers this ability.
	 * 
	 * @param cause The event that caused this ability to trigger.
	 * @param triggerFrom The game state in which this ability triggered.
	 * @return Whether the ability successfully triggered.
	 */
	protected boolean trigger(Event cause, GameState triggerFrom)
	{
		// In case the Trigger Event is used in the intervening-if, set it
		// temporarily.
		this.eventCause = cause.ID;
		EventTriggeredAbility triggered = (EventTriggeredAbility)this.triggerAndReturnNewInstance(triggerFrom);
		this.eventCause = 0;

		// If the trigger triggers successfully
		if(null != triggered)
		{
			// Set the triggers cause
			triggered.eventCause = cause.ID;
			return true;
		}

		return false;
	}

	/**
	 * Triggers this ability.
	 * 
	 * @param cause The zone changes that caused this ability to trigger.
	 * @param triggerFrom The game state in which this ability triggered.
	 */
	protected boolean trigger(ZoneChange cause, GameState triggerFrom)
	{
		// In case the Trigger Event is used in the intervening-if, set it
		// temporarily.
		this.zoneChangeCause = cause;
		EventTriggeredAbility triggered = (EventTriggeredAbility)this.triggerAndReturnNewInstance(triggerFrom);
		this.zoneChangeCause = null;

		if(null != triggered)
		{
			triggered.zoneChangeCause = cause;
			return true;
		}

		return false;
	}

	protected boolean isStopped(ZoneChange cause, GameState state)
	{
		for(EventTriggeredAbilityStopper stopper: this.game.actualState.eventTriggeredAbilityStoppers)
			if(stopper.stops(cause, state))
				return true;

		return false;
	}
}
