package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class ShardsPanorama extends Card
{
	public static final class Fetch extends ActivatedAbility
	{
		private final String thisName;
		private final SubType a;
		private final SubType b;
		private final SubType c;

		public Fetch(GameState state, String thisName, SubType a, SubType b, SubType c)
		{
			super(state, "(1), (T), Sacrifice " + thisName + ": Search your library for a basic " + a + ", " + b + ", or " + c + " card and put it onto the battlefield tapped. Then shuffle your library.");

			this.setManaCost(new ManaPool("1"));
			this.costsTap = true;

			this.thisName = thisName;
			this.a = a;
			this.b = b;
			this.c = c;

			this.addCost(sacrificeThis(thisName));

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, ("Search your library for a basic " + a + ", " + b + ", or " + c + " card and put it onto the battlefield tapped. Then shuffle your library."));
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			factory.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasSubType.instance(a, b, c))));
			this.addEffect(factory);
		}

		@Override
		public Fetch create(Game game)
		{
			return new Fetch(game.physicalState, this.thisName, this.a, this.b, this.c);
		}
	}

	public ShardsPanorama(GameState state, SubType a, SubType b, SubType c)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		this.addAbility(new Fetch(state, this.getName(), a, b, c));
	}
}
