package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CreateTokenCopy extends EventType
{	public static final EventType INSTANCE = new CreateTokenCopy();

	 private CreateTokenCopy()
	{
		super("CREATE_TOKEN_COPY");
	}

	@Override
	public Parameter affects()
	{
		return null;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject original = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		if(original == null)
		{
			event.setResult(Empty.set);
			return false;
		}

		Set tokenCopies = new Set();

		// 110.5a A token is both owned and controlled by the player
		// under whose control it entered the battlefield.
		Player owner = parameters.get(Parameter.CONTROLLER).getOne(Player.class);

		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			number = Sum.get(parameters.get(Parameter.NUMBER));

		java.util.Map<Parameter, Set> tokenParameters = new java.util.HashMap<Parameter, Set>();
		tokenParameters.put(EventType.Parameter.ABILITY, new Set());
		tokenParameters.put(EventType.Parameter.NAME, new Set(""));
		tokenParameters.put(EventType.Parameter.NUMBER, new Set(number));
		tokenParameters.put(EventType.Parameter.CONTROLLER, new Set(owner));
		Event createTokens = createEvent(game, "", CREATE_TOKEN, tokenParameters);

		if(createTokens.perform(event, false))
			for(Token copy: createTokens.getResultGenerator().evaluate(game.physicalState, null).getAll(Token.class))
			{
				copy.ownerID = owner.ID;
				game.physicalState.exileZone().addToTop(copy);
				tokenCopies.add(copy);

				// to keep Identity happy
				game.actualState.put(copy);
			}

		Set result = new Set();
		boolean status = true;

		for(GameObject tokenCopy: tokenCopies.getAll(GameObject.class))
		{
			java.util.Map<Parameter, Set> putAsCopyParameters = new java.util.HashMap<Parameter, Set>();
			putAsCopyParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			putAsCopyParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
			putAsCopyParameters.put(Parameter.OBJECT, new Set(tokenCopy));
			putAsCopyParameters.put(Parameter.SOURCE, parameters.get(Parameter.OBJECT));
			if(parameters.containsKey(Parameter.TYPE))
				putAsCopyParameters.put(Parameter.TYPE, parameters.get(Parameter.TYPE));
			putAsCopyParameters.put(Parameter.TO, new Set(game.actualState.battlefield()));
			Event putAsCopy = createEvent(game, "Put " + (number == 1 ? "a token" : number + " tokens") + " onto the battlefield copying " + original + ".", EventType.PUT_INTO_ZONE_AS_A_COPY_OF, putAsCopyParameters);
			if(!putAsCopy.perform(event, false))
				status = false;

			result.addAll(putAsCopy.getResult());
		}

		event.setResult(result);
		return status;
	}
}