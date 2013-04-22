package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public abstract class Frenzy extends Keyword
{
	protected final int N;

	public Frenzy(GameState state, int N)
	{
		super(state, "Frenzy " + N);
		this.N = N;
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.LinkedList<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new FrenzyAbility(this.state, this.N));
		return ret;
	}

	public static final class FrenzyAbility extends EventTriggeredAbility
	{
		private final int N;

		public FrenzyAbility(GameState state, int N)
		{
			super(state, "Whenever this creature attacks and isn't blocked, it gets +" + N + "/+0 until end of turn.");
			this.N = N;

			SetGenerator thisAbility = This.instance();
			SetGenerator thisCard = AbilitySource.instance(thisAbility);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_UNBLOCKED);
			pattern.put(EventType.Parameter.ATTACKER, thisCard);
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(thisCard, +this.N, +0, "It gets +" + N + "/+0 until end of turn."));
		}

		@Override
		public FrenzyAbility create(Game game)
		{
			return new FrenzyAbility(game.physicalState, this.N);
		}
	}

	public static final class Final extends Frenzy
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
