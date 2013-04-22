package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Putrid Leech")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.LEECH})
@ManaCost("BG")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class PutridLeech extends Card
{
	public static final class PayLifePump extends ActivatedAbility
	{
		public PayLifePump(GameState state)
		{
			super(state, "Pay 2 life: Putrid Leech gets +2/+2 until end of turn. Activate this ability only once each turn.");

			this.addCost(payLife(You.instance(), 2, "Pay 2 life"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+2), (+2), "Putrid Leech gets +2/+2 until end of turn."));
			this.perTurnLimit(1);
		}
	}

	public PutridLeech(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Pay 2 life: Putrid Leech gets +2/+2 until end of turn. Activate this
		// ability only once each turn.
		this.addAbility(new PayLifePump(state));
	}
}
