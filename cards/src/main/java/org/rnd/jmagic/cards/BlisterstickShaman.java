package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Blisterstick Shaman")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SHAMAN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BlisterstickShaman extends Card
{
	public static final class BlisterstickShamanAbility0 extends EventTriggeredAbility
	{
		public BlisterstickShamanAbility0(GameState state)
		{
			super(state, "When Blisterstick Shaman enters the battlefield, it deals 1 damage to target creature or player.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			this.addEffect(permanentDealDamage(1, target, "Blisterstick Shaman deals 1 damage to target creature or player."));
		}
	}

	public BlisterstickShaman(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Blisterstick Shaman enters the battlefield, it deals 1 damage to
		// target creature or player.
		this.addAbility(new BlisterstickShamanAbility0(state));
	}
}
