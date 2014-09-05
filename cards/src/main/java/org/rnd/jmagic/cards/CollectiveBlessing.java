package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Collective Blessing")
@Types({Type.ENCHANTMENT})
@ManaCost("3GGW")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class CollectiveBlessing extends Card
{
	public static final class CollectiveBlessingAbility0 extends StaticAbility
	{
		public CollectiveBlessingAbility0(GameState state)
		{
			super(state, "Creatures you control get +3/+3.");

			this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, +3, +3));
		}
	}

	public CollectiveBlessing(GameState state)
	{
		super(state);

		// Creatures you control get +3/+3.
		this.addAbility(new CollectiveBlessingAbility0(state));
	}
}
