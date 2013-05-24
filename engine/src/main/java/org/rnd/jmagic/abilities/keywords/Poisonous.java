package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Poisonous extends Keyword
{
	protected final int N;

	public Poisonous(GameState state, int N)
	{
		super(state, "Poisonous " + N);
		this.N = N;
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

		ret.add(new PoisonousAbility(this.state, this.N));

		return ret;
	}

	public static final class PoisonousAbility extends EventTriggeredAbility
	{
		private final int N;

		public PoisonousAbility(GameState state, int N)
		{
			super(state, "Whenever this creature deals combat damage to a player, that player gets " + N + " poison counters.");
			this.N = N;

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			EventFactory factory = new EventFactory(EventType.ADD_POISON_COUNTERS, ("That player gets " + N + " poison counters"));
			factory.parameters.put(EventType.Parameter.PLAYER, TakerOfDamage.instance(TriggerDamage.instance(This.instance())));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(this.N));
			this.addEffect(factory);
		}

		@Override
		public PoisonousAbility create(Game game)
		{
			return new PoisonousAbility(game.physicalState, this.N);
		}
	}

	public static final class Final extends Poisonous
	{
		public Final(GameState state, int N)
		{
			super(state, N);
		}

		@Override
		public Final create(Game game)
		{
			return new Final(game.physicalState, this.N);
		}
	}
}
