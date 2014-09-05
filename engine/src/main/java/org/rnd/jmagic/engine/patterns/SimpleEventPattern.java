package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

/**
 * Used to determine whether an event matches a set of criteria. Instead of
 * matching against a Set returned by each parameter evaluated, each Set
 * returned by a parameter evaluation is matched against a SetPattern
 */
public class SimpleEventPattern implements EventPattern
{
	private final EventType type;
	private final java.util.Map<EventType.Parameter, SetPattern> parameters;
	private SetPattern result;

	public boolean matchesManaAbilities;

	public SimpleEventPattern(EventType type)
	{
		this.type = type;
		this.parameters = new java.util.HashMap<EventType.Parameter, SetPattern>();
		this.result = null;
	}

	/**
	 * 603.6d ... Abilities that trigger when a permanent phases out, ...
	 * abilities that trigger specifically when an object becomes unattached,
	 * abilities that trigger when a player loses control of an object, and
	 * abilities that trigger when a player planeswalks away from a plane will
	 * trigger based on their existence, and the appearance of objects, prior to
	 * the event rather than afterward.
	 *
	 * This function determines whether this trigger is one of those abilities.
	 *
	 * TODO : when a permanent phases out
	 *
	 * TODO : when a player loses control of an object
	 *
	 * @return Whether the ability looks back in time.
	 */
	@Override
	public boolean looksBackInTime()
	{
		if(EventType.DESTROY_ONE_PERMANENT == this.type)
			return true;

		if(EventType.SACRIFICE_ONE_PERMANENT == this.type)
			return true;

		if(EventType.UNATTACH == this.type)
			return true;

		return false;
	}

	/**
	 * Determines whether a given event matches this pattern. Classes that
	 * override this method should call it (using super.match).
	 *
	 * @param event The event to check.
	 * @param object The object evaluating this generator (null if a non-object
	 * is causing this match).
	 * @param state The game state in which to check the event against the
	 * pattern.
	 * @return True if the event matches this pattern; false otherwise.
	 */
	@Override
	public boolean match(Event event, Identified object, GameState state)
	{
		if(event.type != this.type)
			return false;

		for(EventType.Parameter parameterName: this.parameters.keySet())
		{
			if(!event.parameters.containsKey(parameterName))
				return false;

			GameObject eventSource = event.getSource();

			Set thisParameter;
			if(event.wasPerformed())
				thisParameter = event.parametersNow.get(parameterName).evaluate(state, eventSource);
			else
				thisParameter = event.parameters.get(parameterName).evaluate(state, eventSource);
			if(!this.parameters.get(parameterName).match(state, object, thisParameter))
				return false;
		}

		if(this.result != null)
			if(!this.result.match(state, object, event.getResult(state)))
				return false;

		return true;
	}

	/**
	 * Adds a parameter to this event pattern.
	 *
	 * @param parameter The name of the parameter.
	 * @param generator The value of the parameter, around which a
	 * SimpleSetPattern will be constructed.
	 */
	public final void put(EventType.Parameter parameter, SetGenerator generator)
	{
		this.parameters.put(parameter, new SimpleSetPattern(generator));
	}

	public final void put(EventType.Parameter parameter, SetPattern pattern)
	{
		this.parameters.put(parameter, pattern);
	}

	public final void withResult(SetGenerator result)
	{
		this.result = new SimpleSetPattern(result);
	}

	public final void withResult(SetPattern result)
	{
		this.result = result;
	}

	@Override
	public boolean matchesManaAbilities()
	{
		return this.matchesManaAbilities;
	}
}
