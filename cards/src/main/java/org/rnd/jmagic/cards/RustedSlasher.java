package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rusted Slasher")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.HORROR})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({})
public final class RustedSlasher extends Card
{
	public static final class RustedSlasherAbility0 extends ActivatedAbility
	{
		public RustedSlasherAbility0(GameState state)
		{
			super(state, "Sacrifice an artifact: Regenerate Rusted Slasher.");
			// Sacrifice an artifact
			this.addCost(sacrifice(You.instance(), 1, HasType.instance(Type.ARTIFACT), "Sacrifice an artifact"));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Rusted Slasher."));
		}
	}

	public RustedSlasher(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		// Sacrifice an artifact: Regenerate Rusted Slasher.
		this.addAbility(new RustedSlasherAbility0(state));
	}
}
