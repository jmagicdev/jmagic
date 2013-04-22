package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rage Thrower")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RageThrower extends Card
{
	public static final class RageThrowerAbility0 extends EventTriggeredAbility
	{
		public RageThrowerAbility0(GameState state)
		{
			super(state, "Whenever another creature dies, Rage Thrower deals 2 damage to target player.");
			this.addPattern(whenXDies(RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS)));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(permanentDealDamage(2, target, "Rage Thrower deals 2 damage to target player."));
		}
	}

	public RageThrower(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Whenever another creature dies, Rage Thrower deals 2 damage to target
		// player.
		this.addAbility(new RageThrowerAbility0(state));
	}
}
