package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Look extends EventType
{
	public static final EventType INSTANCE = new Look();

	private Look()
	{
		super("LOOK");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set objects = parameters.get(Parameter.OBJECT);
		java.util.Set<GameObject> gameObjects = objects.getAll(GameObject.class);
		for(Zone z: objects.getAll(Zone.class))
			gameObjects.addAll(z.objects);
		java.util.Set<Player> players = parameters.get(Parameter.PLAYER).getAll(Player.class);
		Set ret = new Set();

		for(Player player: players)
		{
			org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent.Look(event, player, gameObjects);
			player.alert(sanitized);
		}

		GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
		for(GameObject object: gameObjects)
		{
			// Change the visibleTo property of the actual object. If there
			// is a duration, the FCE will take over. Otherwise it will
			// revert next time the game state refreshes.
			object = game.actualState.copyForEditing(object);
			for(Player player: players)
				object.setActualVisibility(player, true);
			ret.add(object);
		}

		SetGenerator expiration;
		if(parameters.containsKey(Parameter.EFFECT))
		{
			expiration = parameters.get(Parameter.EFFECT).getOne(SetGenerator.class);
			if(expiration == null)
				throw new UnsupportedOperationException(cause + ": LOOK.EFFECT didn't contain a SetGenerator");
		}
		else
			expiration = Not.instance(Exists.instance(Identity.instance(cause)));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.LOOK);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.fromCollection(objects));
		part.parameters.put(ContinuousEffectType.Parameter.PLAYER, Identity.fromCollection(players));

		java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
		lookParameters.put(Parameter.CAUSE, new Set(cause));
		lookParameters.put(Parameter.EFFECT, new Set(part));
		lookParameters.put(Parameter.EXPIRES, new Set(expiration));
		createEvent(game, event.getName(), CREATE_FLOATING_CONTINUOUS_EFFECT, lookParameters).perform(event, false);

		event.setResult(Identity.fromCollection(ret));
		return true;
	}
}