package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class TapHard extends EventType
{
	public static final EventType INSTANCE = new TapHard();

	private TapHard()
	{
		super("TAP_HARD");
	}

	@Override
	public Parameter affects()
	{
		return TAP_PERMANENTS.affects();
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		return TAP_PERMANENTS.attempt(game, event, parameters);
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set objects = parameters.get(Parameter.OBJECT);

		Event tap = createEvent(game, "Tap " + objects, EventType.TAP_PERMANENTS, parameters);
		boolean status = tap.perform(event, false);
		event.setResult(tap.getResultGenerator());

		// Gotta do this one by one. See comments inside the loop for why
		// this is true.
		for(GameObject object: objects.getAll(GameObject.class))
		{
			// This event pattern matches the untap step of an arbitrary
			// controller of any of the given objects.
			EventPattern untapping = new UntapDuringControllersUntapStep(Identity.instance(object));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untapping));

			// These generators evaluate differently for different players,
			// and if objects change controllers, they'll even evaluate
			// differently at different times.
			SetGenerator thatPlayersUntap = UntapStepOf.instance(ControllerOf.instance(Identity.instance(object)));
			SetGenerator untapStepOver = Intersect.instance(PreviousStep.instance(), thatPlayersUntap);

			java.util.Map<Parameter, Set> ctsEffectParameters = new java.util.HashMap<Parameter, Set>();
			ctsEffectParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			ctsEffectParameters.put(Parameter.EFFECT, new Set(part));
			ctsEffectParameters.put(Parameter.EXPIRES, new Set(untapStepOver));
			Event noUntap = createEvent(game, object + " doesn't untap during its controller's next untap step.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, ctsEffectParameters);
			noUntap.perform(event, false);
		}

		return status;
	}

}