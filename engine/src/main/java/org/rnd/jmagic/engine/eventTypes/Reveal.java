package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Reveal extends EventType
{	public static final EventType INSTANCE = new Reveal();

	 private Reveal()
	{
		super("REVEAL");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set objectParameter = parameters.get(Parameter.OBJECT);
		java.util.Set<GameObject> objects = objectParameter.getAll(GameObject.class);
		for(Zone z: objectParameter.getAll(Zone.class))
			objects.addAll(z.objects);
		Set ret = new Set();

		Set cause = parameters.get(Parameter.CAUSE);
		for(GameObject object: objects)
		{
			// Change the visibleTo property of the actual object. The FCE
			// will take over afterward.
			object = game.actualState.copyForEditing(object);
			for(Player player: game.actualState.players)
				object.setActualVisibility(player, true);
			ret.add(object);
		}

		for(Player player: game.actualState.players)
		{
			org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent.Reveal(event, objects, player);
			player.alert(sanitized);
		}

		SetGenerator expiration;
		if(parameters.containsKey(Parameter.EFFECT))
		{
			expiration = parameters.get(Parameter.EFFECT).getOne(SetGenerator.class);
			if(expiration == null)
				throw new UnsupportedOperationException(cause + ": REVEAL.EFFECT didn't contain a SetGenerator!");
		}
		else
			expiration = Not.instance(Exists.instance(Identity.instance(cause)));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REVEAL);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(ret));

		java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
		revealParameters.put(Parameter.CAUSE, cause);
		revealParameters.put(Parameter.EFFECT, new Set(part));
		revealParameters.put(Parameter.EXPIRES, new Set(expiration));
		createEvent(game, event.getName(), CREATE_FLOATING_CONTINUOUS_EFFECT, revealParameters).perform(event, false);

		event.setResult(Identity.instance(ret));
		return true;
	}
}