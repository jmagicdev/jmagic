package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Second Guess")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SecondGuess extends Card
{
	public static class NthSpellCastThisTurn extends SetGenerator
	{
		public static SetGenerator instance(int n)
		{
			return new NthSpellCastThisTurn(n);
		}

		private int n;

		private NthSpellCastThisTurn(int n)
		{
			this.n = n;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			java.util.List<Integer> spells = state.getTracker(org.rnd.jmagic.abilities.keywords.Storm.StormTracker.class).getValue(state);
			if(spells.size() < this.n)
				return Empty.set;
			return new Set(state.get(spells.get(this.n - 1)));
		}
	}

	public SecondGuess(GameState state)
	{
		super(state);

		// Counter target spell that's the second spell cast this turn.
		state.ensureTracker(new org.rnd.jmagic.abilities.keywords.Storm.StormTracker());
		SetGenerator target = targetedBy(this.addTarget(NthSpellCastThisTurn.instance(2), "target spell that's the second spell cast this turn"));
		this.addEffect(counter(target, "Counter target spell that's the second spell cast this turn."));
	}
}
