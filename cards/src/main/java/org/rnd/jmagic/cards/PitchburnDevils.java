package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Pitchburn Devils")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class PitchburnDevils extends Card
{
	public static final class PitchburnDevilsAbility0 extends EventTriggeredAbility
	{
		public PitchburnDevilsAbility0(GameState state)
		{
			super(state, "When Pitchburn Devils dies, it deals 3 damage to target creature or player.");
			this.addPattern(whenThisDies());
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(3, target, "It deals 3 damage to target creature or player."));
		}
	}

	public PitchburnDevils(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Pitchburn Devils dies, it deals 3 damage to target creature or
		// player.
		this.addAbility(new PitchburnDevilsAbility0(state));
	}
}
