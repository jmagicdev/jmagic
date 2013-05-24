package org.rnd.jmagic.engine.eventTypes;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class TakeExtraTurn extends EventType
{	public static final EventType INSTANCE = new TakeExtraTurn();

	 private TakeExtraTurn()
	{
		super("TAKE_EXTRA_TURN");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			number = Sum.get(parameters.get(Parameter.NUMBER));

		Set result = new Set();
		for(int i = 0; i < number; i++)
			result.add(game.physicalState.addExtraTurn(player));

		if(parameters.containsKey(Parameter.STEP))
			for(Step.StepType step: parameters.get(Parameter.STEP).getAll(Step.StepType.class))
				for(Turn turn: result.getAll(Turn.class))
				{
					SimpleEventPattern extraTurnsStep = new SimpleEventPattern(EventType.BEGIN_STEP);
					extraTurnsStep.put(EventType.Parameter.STEP, StepOf.instance(Identity.instance(turn), step.phase, step));

					EventReplacementEffect skipReplacement = new EventReplacementEffect(game, "Skip " + step.name() + " step of extra turn.", extraTurnsStep);
					ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
					part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(skipReplacement));

					java.util.Map<Parameter, Set> skipParameters = new java.util.HashMap<Parameter, Set>();
					skipParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
					skipParameters.put(EventType.Parameter.EFFECT, new Set(part));
					skipParameters.put(EventType.Parameter.EXPIRES, new Set(Empty.instance()));
					skipParameters.put(EventType.Parameter.USES, ONE);
					Event skipStep = createEvent(game, "Skip " + step.name() + " step of extra turn.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, skipParameters);

					skipStep.perform(event, false);
				}

		event.setResult(Identity.instance(result));
		return true;
	}
}