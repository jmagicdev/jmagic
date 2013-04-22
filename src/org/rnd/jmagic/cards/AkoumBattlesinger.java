package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Akoum Battlesinger")
@Types({Type.CREATURE})
@SubTypes({SubType.ALLY, SubType.BERSERKER, SubType.HUMAN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class AkoumBattlesinger extends Card
{
	public static final class PlusOnePower extends EventTriggeredAbility
	{
		public PlusOnePower(GameState state)
		{
			super(state, "Whenever Akoum Battlesinger or another Ally enters the battlefield under your control, you may have Ally creatures you control get +1/+0 until end of turn.");
			this.addPattern(allyTrigger());

			EventFactory pump = ptChangeUntilEndOfTurn(ALLIES_YOU_CONTROL, +1, +0, "Ally creatures you control get +1/+0 until end of turn");
			this.addEffect(youMay(pump, "You may have Ally creatures you control get +1/+0 until end of turn."));
		}
	}

	public AkoumBattlesinger(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Whenever Akoum Battlesinger or another Ally enters the battlefield
		// under your control, you may have Ally creatures you control get +1/+0
		// until end of turn.
		this.addAbility(new PlusOnePower(state));
	}
}
