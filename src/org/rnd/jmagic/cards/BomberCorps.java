package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Bomber Corps")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BomberCorps extends Card
{
	public static final class BomberCorpsAbility0 extends EventTriggeredAbility
	{
		public BomberCorpsAbility0(GameState state)
		{
			super(state, "Whenever Bomber Corps and at least two other creatures attack, Bomber Corps deals 1 damage to target creature or player.");

			this.addPattern(battalion());

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			this.addEffect(permanentDealDamage(1, target, "Bomber Corps deals 1 damage to target creature or player."));
		}
	}

	public BomberCorps(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Battalion \u2014 Whenever Bomber Corps and at least two other
		// creatures attack, Bomber Corps deals 1 damage to target creature or
		// player.
		this.addAbility(new BomberCorpsAbility0(state));
	}
}
