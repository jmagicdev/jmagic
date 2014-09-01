package org.rnd.jmagic.engine.eventTypes;


import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Monstrosity extends EventType
{
	public static final EventType INSTANCE = new Monstrosity();

	public static class MonstrousTracker extends Tracker<java.util.Set<Integer>>
	{
		private java.util.Set<Integer> values = new java.util.HashSet<Integer>();
		private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.values);

		@Override
		protected MonstrousTracker clone()
		{
			MonstrousTracker ret = (MonstrousTracker)super.clone();
			ret.values = new java.util.HashSet<Integer>(this.values);
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.values);
			return ret;
		}
		@Override
		protected java.util.Set<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void reset()
		{
			// purposefully non-functional. permanents do not unmonstrous.
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return event.type == EventType.BECOMES_MONSTROUS;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			for(GameObject object: event.parameters.get(Parameter.OBJECT).evaluate(state, null).getAll(GameObject.class))
				this.values.add(object.ID);
		}
	}

	private Monstrosity()
	{
		super("MONSTROSITY");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<>(parameters);
		parameters.put(Parameter.COUNTER, new Set(Counter.CounterType.PLUS_ONE_PLUS_ONE));
		return PUT_COUNTERS.attempt(game, event, newParameters);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(Empty.set);

		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		if(object == null)
			// this will happen if the object is killed in response to the
			// monstrous ability
			return false;

		java.util.Set<Integer> monstrousThings = game.actualState.getTracker(MonstrousTracker.class).getValue(game.actualState);
		if(monstrousThings.contains(object.ID))
			return true;

		int N = parameters.get(Parameter.NUMBER).getOne(Integer.class);

		java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<>();
		Set objectParameter = new Set(object);
		newParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		newParameters.put(Parameter.OBJECT, objectParameter);
		newParameters.put(Parameter.COUNTER, new Set(Counter.CounterType.PLUS_ONE_PLUS_ONE));
		newParameters.put(Parameter.NUMBER, new Set(N));
		createEvent(game, "Put " + org.rnd.util.NumberNames.get(N) + " +1/+1 counter" + (N == 1 ? "" : "s") + " on " + object + ".", EventType.PUT_COUNTERS, newParameters).perform(event, false);

		createEvent(game, "" + object + " becomes monstrous.", EventType.BECOMES_MONSTROUS, java.util.Collections.singletonMap(Parameter.OBJECT, objectParameter)).perform(event, false);

		return true;
	}
}