package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CreateFloatingContinuousEffect extends EventType
{
	public static final EventType INSTANCE = new CreateFloatingContinuousEffect();

	private CreateFloatingContinuousEffect()
	{
		super("CREATE_FLOATING_CONTINUOUS_EFFECT");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.EFFECT; // awkward...
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);

		java.util.Set<FloatingContinuousEffect> physicalEffects = new java.util.HashSet<FloatingContinuousEffect>();

		FloatingContinuousEffect newEffect = new FloatingContinuousEffect(game, event.getName());

		// keep Identity happy
		game.actualState.put(newEffect);

		if(parameters.containsKey(Parameter.EFFECT))
			for(ContinuousEffect.Part p: parameters.get(Parameter.EFFECT).getAll(ContinuousEffect.Part.class))
				newEffect.parts.add(p.clone());
		if(parameters.containsKey(Parameter.EXPIRES))
		{
			newEffect.expires = parameters.get(Parameter.EXPIRES).getOne(SetGenerator.class);
			if(newEffect.expires == null)
				throw new IllegalStateException("EXPIRES parameter of '" + event + "' didn't contain a SetGenerator.");
		}
		if(parameters.containsKey(Parameter.PREVENT))
		{
			Set prevent = parameters.get(Parameter.PREVENT);
			SetGenerator whoTo = prevent.getOne(SetGenerator.class);
			if(whoTo == null)
				throw new IllegalStateException("PREVENT parameter of '" + event + "' didn't contain a SetGenerator.");
			newEffect.addDamagePreventionShield(whoTo, prevent.getOne(Integer.class));
		}
		if(parameters.containsKey(Parameter.USES))
		{
			Integer number = Sum.get(parameters.get(Parameter.USES));
			newEffect.uses = number;
		}
		if(parameters.containsKey(Parameter.DAMAGE))
		{
			Integer number = Sum.get(parameters.get(Parameter.DAMAGE));
			newEffect.damage = number;
		}
		if(0 < newEffect.parts.size())
			physicalEffects.add(newEffect);

		for(FloatingContinuousEffect effect: physicalEffects)
		{
			for(ContinuousEffect.Part part: effect.parts)
			{
				// 611.2c ... A continuous effect generated by the
				// resolution of a spell or ability that doesn't modify the
				// characteristics or change the controller of any objects
				// modifies the rules of the game, so it can affect objects
				// that weren't affected when that continuous effect began.
				if(part.type.layer() == ContinuousEffectType.Layer.RULE_CHANGE)
					continue;

				for(java.util.Map.Entry<ContinuousEffectType.Parameter, SetGenerator> parameter: part.parameters.entrySet())
				{
					ContinuousEffectType.Parameter parameterName = parameter.getKey();
					Set evaluation = parameter.getValue().evaluate(game, cause);

					SetGenerator newParameter = Identity.fromCollection(evaluation);
					part.parameters.put(parameterName, newParameter);
				}
			}
			effect.sourceEvent = event;

			// 613.6b A continuous effect generated by the resolution of a
			// spell or ability receives a timestamp at the time it's
			// created.
			effect.timestamp = game.physicalState.getNextAvailableTimestamp();
			effect.turnCreated = game.physicalState.currentTurn();
			game.physicalState.floatingEffects.add(effect);
		}
		event.setResult(Identity.fromCollection(physicalEffects));
		return true;
	}
}