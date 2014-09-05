package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CopySpellCard extends EventType
{
	public static final EventType INSTANCE = new CopySpellCard();

	private CopySpellCard()
	{
		super("COPY_SPELL_CARD");
	}

	@Override
	public Parameter affects()
	{
		return null;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set result = new Set();

		int number = (parameters.containsKey(Parameter.NUMBER) ? Sum.get(parameters.get(Parameter.NUMBER)) : 1);
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		for(int i = 0; i < number; i++)
		{
			GameObject copy = new SpellCopy(game.physicalState, object.getName());
			copy.setOwner(object.getOwner(game.actualState));
			// this is to keep the move event from crashing when it tries to
			// figure out where to pull the card from
			game.physicalState.exileZone().addToTop(copy);
			// keep Identity happy
			game.actualState.put(copy);
			
			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			moveParameters.put(Parameter.OBJECT, new Set(copy));
			moveParameters.put(Parameter.SOURCE, new Set(object));
			moveParameters.put(Parameter.TO, new Set(object.getZone()));
			Event makeCopy = createEvent(game, "Copy " + object + ".", EventType.PUT_INTO_ZONE_AS_A_COPY_OF, moveParameters);
			makeCopy.perform(event, true);

			result.addAll(makeCopy.getResult());
		}
		

		event.setResult(result);
		return true;
	}
}