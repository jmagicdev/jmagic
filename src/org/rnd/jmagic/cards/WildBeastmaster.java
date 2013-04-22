package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wild Beastmaster")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class WildBeastmaster extends Card
{
	public static final class WildBeastmasterAbility0 extends EventTriggeredAbility
	{
		public WildBeastmasterAbility0(GameState state)
		{
			super(state, "Whenever Wild Beastmaster attacks, each other creature you control gets +X/+X until end of turn, where X is Wild Beastmaster's power.");
			this.addPattern(whenThisAttacks());

			SetGenerator what = RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS);
			SetGenerator pt = PowerOf.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(ptChangeUntilEndOfTurn(what, pt, pt, "Each other creature you control gets +X/+X until end of turn, where X is Wild Beastmaster's power."));
		}
	}

	public WildBeastmaster(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever Wild Beastmaster attacks, each other creature you control
		// gets +X/+X until end of turn, where X is Wild Beastmaster's power.
		this.addAbility(new WildBeastmasterAbility0(state));
	}
}
