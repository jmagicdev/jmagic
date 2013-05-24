package org.rnd.jmagic.engine.eventTypes;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PayCumulativeUpkeep extends EventType
{	public static final EventType INSTANCE = new PayCumulativeUpkeep();

	 private PayCumulativeUpkeep()
	{
		super("PAY_CUMULATIVE_UPKEEP");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.EVENT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Event costEvent = parameters.get(Parameter.EVENT).getOne(EventFactory.class).createEvent(game, event.getSource());
		int number = Sum.get(parameters.get(Parameter.NUMBER));

		if(0 >= number)
		{
			event.setResult(Empty.set);
			return true;
		}

		/*
		 * Kamikaze: But then we're forcing all cumulative upkeep payment events 
		 *       to use the NUMBER parameter
		 * RulesGuru: So?
		 * Kamikaze: Ok, I'm putting this conversation in a comment right above
		 *       that change so that when you yell at me, I can yell back.
		 */
		if(costEvent.parameters.containsKey(Parameter.NUMBER))
			costEvent.parameters.put(Parameter.NUMBER, Multiply.instance(costEvent.parameters.get(Parameter.NUMBER), numberGenerator(number)));
		else
			costEvent.parameters.put(Parameter.NUMBER, Identity.instance(number));

		boolean status = costEvent.perform(event, false);

		event.setResult(costEvent.getResultGenerator());
		return status;
	}
}