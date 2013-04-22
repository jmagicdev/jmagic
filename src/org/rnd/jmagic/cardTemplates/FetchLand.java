package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class FetchLand extends Card
{
	public static final class Fetch extends ActivatedAbility
	{
		private final String thisName;
		private final SubType a;
		private final SubType b;

		public Fetch(GameState state, String thisName, SubType a, SubType b)
		{
			super(state, "(T), Pay 1 life, Sacrifice " + thisName + ": Search your library for a " + a + " or " + b + " card and put it onto the battlefield. Then shuffle your library.");

			this.costsTap = true;

			this.thisName = thisName;
			this.a = a;
			this.b = b;

			this.addCost(payLife(You.instance(), 1, "Pay 1 life"));
			this.addCost(sacrificeThis(thisName));

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, ("Search your library for a " + a + " or " + b + " card and put it onto the battlefield. Then shuffle your library."));
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(a, b)));
			this.addEffect(factory);
		}

		@Override
		public Fetch create(Game game)
		{
			return new Fetch(game.physicalState, this.thisName, this.a, this.b);
		}
	}

	public FetchLand(GameState state, SubType a, SubType b)
	{
		super(state);

		this.addAbility(new Fetch(state, this.getName(), a, b));
	}
}
