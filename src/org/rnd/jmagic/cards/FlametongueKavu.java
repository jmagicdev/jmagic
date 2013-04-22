package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flametongue Kavu")
@Types({Type.CREATURE})
@SubTypes({SubType.KAVU})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.PLANESHIFT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class FlametongueKavu extends Card
{
	public static final class FlametongueKavuAbility0 extends EventTriggeredAbility
	{
		public FlametongueKavuAbility0(GameState state)
		{
			super(state, "When Flametongue Kavu enters the battlefield, it deals 4 damage to target creature.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(4, target, "Flametongue Kavu deals 4 damage to target creature."));
		}
	}

	public FlametongueKavu(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// When Flametongue Kavu enters the battlefield, it deals 4 damage to
		// target creature.
		this.addAbility(new FlametongueKavuAbility0(state));
	}
}
