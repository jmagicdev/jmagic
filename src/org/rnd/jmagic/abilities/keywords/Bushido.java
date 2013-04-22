package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

public final class Bushido extends Keyword
{
	// 702.42. Bushido
	//
	// 702.42a Bushido is a triggered ability. "Bushido N" means
	// "Whenever this creature blocks or becomes blocked, it gets +N/+N until end of turn."
	// (See rule 509, "Declare Blockers Step.")

	private final int N;

	public Bushido(GameState state, int N)
	{
		super(state, "Bushido " + N);
		this.N = N;
	}

	@Override
	public Bushido create(Game game)
	{
		return new Bushido(game.physicalState, this.N);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.LinkedList<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new BushidoAbility(this.state, this.N));
		return ret;
	}

	public static final class BushidoAbility extends EventTriggeredAbility
	{
		private final int N;

		public BushidoAbility(GameState state, int N)
		{
			super(state, "Whenever this creature blocks or becomes blocked, it gets " + formatN(N) + "/" + formatN(N) + " until end of turn.");
			this.N = N;

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			this.addPattern(whenThisBlocks());

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED);
			pattern.put(EventType.Parameter.ATTACKER, thisCard);
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(thisCard, N, N, "It gets " + formatN(N) + "/" + formatN(N) + " until end of turn."));
		}

		@Override
		public BushidoAbility create(Game game)
		{
			return new BushidoAbility(game.physicalState, this.N);
		}

		public static String formatN(int N)
		{
			if(N < 0)
				return "-" + (0 - N);
			return "+" + N;
		}
	}
}
