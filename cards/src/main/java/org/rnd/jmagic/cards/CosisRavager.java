package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cosi's Ravager")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class CosisRavager extends Card
{
	public static final class CosisRavagerAbility0 extends EventTriggeredAbility
	{
		public CosisRavagerAbility0(GameState state)
		{
			super(state, "Landfall \u2014 Whenever a land enters the battlefield under your control, you may have Cosi's Ravager deal 1 damage to target player.");
			this.addPattern(landfall());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			EventFactory damage = permanentDealDamage(1, target, "Cosi's Ravager deals 1 damage to target player");
			this.addEffect(youMay(damage, "You may have Cosi's Ravager deal 1 damage to target player."));
		}
	}

	public CosisRavager(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may have Cosi's Ravager deal 1 damage to target player.
		this.addAbility(new CosisRavagerAbility0(state));
	}
}
