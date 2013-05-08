package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tempered Steel")
@Types({Type.ENCHANTMENT})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class TemperedSteel extends Card
{
	public static final class TemperedSteelAbility0 extends StaticAbility
	{
		public TemperedSteelAbility0(GameState state)
		{
			super(state, "Artifact creatures you control get +2/+2.");

			this.addEffectPart(modifyPowerAndToughness(Intersect.instance(CREATURES_YOU_CONTROL, ArtifactPermanents.instance()), +2, +2));
		}
	}

	public TemperedSteel(GameState state)
	{
		super(state);

		// Artifact creatures you control get +2/+2.
		this.addAbility(new TemperedSteelAbility0(state));
	}
}
