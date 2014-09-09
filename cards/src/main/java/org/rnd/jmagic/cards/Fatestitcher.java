package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fatestitcher")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.ZOMBIE})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class Fatestitcher extends Card
{
	public static final class FatestitcherAbility0 extends ActivatedAbility
	{
		public FatestitcherAbility0(GameState state)
		{
			super(state, "(T): You may tap or untap another target permanent.");
			this.costsTap = true;

			Target target = this.addTarget(RelativeComplement.instance(Permanents.instance(), ABILITY_SOURCE_OF_THIS), "target permanent");
			this.addEffect(youMay(tapOrUntap(targetedBy(target), "another target permanent"), "You may tap or untap another target permanent."));
		}
	}

	public Fatestitcher(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// (T): You may tap or untap another target permanent.
		this.addAbility(new FatestitcherAbility0(state));

		// Unearth (U)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unearth(state, "(U)"));
	}
}
