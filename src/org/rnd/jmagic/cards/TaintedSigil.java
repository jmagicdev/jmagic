package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tainted Sigil")
@Types({Type.ARTIFACT})
@ManaCost("1WB")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class TaintedSigil extends Card
{
	public static final class GainLife extends ActivatedAbility
	{
		public GainLife(GameState state)
		{
			super(state, "(T), Sacrifice Tainted Sigil: You gain life equal to the total life lost by all players this turn.");

			// (T),
			this.costsTap = true;

			// Sacrifice Tainted Sigil:
			this.addCost(sacrificeThis("Tainted Sigil"));

			// You gain life equal to the total life lost by all players this
			// turn.
			state.ensureTracker(new LifeLostThisTurn.LifeTracker());
			this.addEffect(gainLife(You.instance(), LifeLostThisTurn.instance(Players.instance()), "You gain life equal to the total life lost by all players this turn."));
		}
	}

	public TaintedSigil(GameState state)
	{
		super(state);

		this.addAbility(new GainLife(state));
	}
}
