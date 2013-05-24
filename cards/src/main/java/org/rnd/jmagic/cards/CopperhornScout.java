package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Copperhorn Scout")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SCOUT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class CopperhornScout extends Card
{
	public static final class CopperhornScoutAbility0 extends EventTriggeredAbility
	{
		public CopperhornScoutAbility0(GameState state)
		{
			super(state, "Whenever Copperhorn Scout attacks, untap each other creature you control.");
			this.addPattern(whenThisAttacks());
			this.addEffect(untap(RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS), "Untap each other creature you control."));
		}
	}

	public CopperhornScout(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever Copperhorn Scout attacks, untap each other creature you
		// control.
		this.addAbility(new CopperhornScoutAbility0(state));
	}
}
