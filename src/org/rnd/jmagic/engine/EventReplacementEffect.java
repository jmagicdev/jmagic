package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/**
 * Represents a replacement effect that replaces something other than damage.
 * For any replacement effect that modifies the event rather than creates a new
 * one, seriously consider using the {@link Impersonate} set generator for each
 * parameter. The CAUSE parameter, if present, will already be transferred using
 * {@link Impersonate}.
 */
public class EventReplacementEffect extends ReplacementEffect implements Iterable<EventFactory>
{
	private EventPattern toReplace;
	private final java.util.List<EventFactory> replaceWith;

	private int isReplacing;

	/**
	 * Create a EventReplacementEffect that skips matched events.
	 * 
	 * @param game The game this replacement effect is part of
	 * @param name What the replacement effect does
	 */
	private EventReplacementEffect(Game game, String name)
	{
		super(game, name);
		this.replaceWith = new java.util.LinkedList<EventFactory>();
	}

	/**
	 * Create a EventReplacementEffect that skips matched events.
	 * 
	 * @param game The game this replacement effect is part of
	 * @param name What the replacement effect does
	 * @param toReplace Describes what events this effect applies to.
	 */
	public EventReplacementEffect(Game game, String name, EventPattern toReplace)
	{
		this(game, name);
		this.toReplace = toReplace;
	}

	/**
	 * @param factory The components to construct the event
	 */
	public void addEffect(EventFactory factory)
	{
		this.replaceWith.add(factory);
	}

	/**
	 * Determines whether this replacement effect can apply to an event.
	 * 
	 * @param event The event to check
	 * @return True if this replacement effect applies to the specified event,
	 * hasn't replaced it before, and isn't out of uses. False otherwise.
	 */
	public boolean appliesTo(Event event)
	{
		// if this event is out of uses, it can't do diddly
		FloatingContinuousEffect parentFCE = this.getFloatingContinuousEffect(this.game.actualState);
		if(null != parentFCE && parentFCE.uses == 0)
			return false;

		// if this effect has already replaced this event, it can't replace it
		// again
		for(ReplacementEffect replacement: event.replacedBy)
			if(this == replacement)
				return false;

		this.isReplacing = event.ID;
		if(this.isStopped())
			return false;
		return this.toReplace.match(event, this.getSourceObject(this.game.actualState), this.game.actualState);
	}

	/**
	 * Applies this replacement effect to an event.
	 * 
	 * @param toReplace The event to replace
	 * @return The sequence of events that results from applying the replacement
	 * effect.
	 */
	public java.util.List<Event> apply(Event toReplace)
	{
		this.isReplacing = toReplace.ID;

		if(!this.apply(this.game.actualState))
		{
			java.util.List<Event> ret = new java.util.LinkedList<Event>();
			toReplace.replacedBy.add(this);
			ret.add(toReplace);
			return ret;
		}

		java.util.List<Event> newEvents = new java.util.LinkedList<Event>();
		for(EventFactory replaceWith: this.replaceWith)
		{
			// The new event has the same source as this replacement effect's
			// source, unless it doesn't have one, then it's the game
			Event newEvent = replaceWith.createEvent(this.game, (GameObject)this.getSourceObject(this.game.actualState));

			SetGenerator replacedEventsSource = EventSource.instance(ReplacedBy.instance(Identity.instance(this)));

			// If a parameter isn't specified in replaceWith, then the
			// replacement effect generates the same parameter as the one in
			// the replaced event.
			for(EventType.Parameter parameterName: toReplace.parameters.keySet())
				// Replacement effects _always_ maintain their cause. See
				// Impersonate's documentation for specifics.
				if(!replaceWith.parameters.containsKey(parameterName))
				{
					SetGenerator replacedEventsCauseGenerator = toReplace.parameters.get(parameterName);
					newEvent.parameters.put(parameterName, Impersonate.instance(replacedEventsSource, replacedEventsCauseGenerator));
				}

			// the new event can't be replaced by this effect...
			newEvent.replacedBy.add(this);
			// ... or by anything that replaced the old event
			newEvent.replacedBy.addAll(toReplace.replacedBy);

			newEvents.add(newEvent);
		}

		FloatingContinuousEffect parentFCE = this.getFloatingContinuousEffect(this.game.actualState);
		if(null != parentFCE && parentFCE.uses > 0)
			--parentFCE.getPhysical().uses;

		return newEvents;
	}

	/**
	 * @return An unmodifiable list of what this effect will replace events
	 * with. Needed to determine what objects this effect refers to, for rule
	 * 609.7a.
	 * @see org.rnd.jmagic.engine.generators.AllSourcesOfDamage
	 */
	public java.util.List<EventFactory> getEffects()
	{
		return java.util.Collections.unmodifiableList(this.replaceWith);
	}

	/** @return The event this effect is currently replacing. */
	public Event isReplacing()
	{
		return this.game.physicalState.get(this.isReplacing);
	}

	public boolean isStopped()
	{
		for(EventReplacementEffectStopper stopper: this.game.actualState.eventReplacementEffectStoppers)
			if(stopper.stops(this, this.isReplacing()))
				return true;

		return false;
	}

	/**
	 * @return An iterator over the events this effect creates when it replaces
	 * something.
	 */
	@Override
	public java.util.Iterator<EventFactory> iterator()
	{
		return this.replaceWith.iterator();
	}

	@Override
	public java.util.Collection<GameObject> refersTo(GameState state)
	{
		java.util.Collection<GameObject> ret = new java.util.HashSet<GameObject>();
		Identified source = this.getSourceObject(state);
		for(EventFactory effect: this.replaceWith)
			ret.addAll(effect.refersTo(state, source));
		return ret;
	}
}
