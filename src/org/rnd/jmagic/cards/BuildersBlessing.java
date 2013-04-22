package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Builder's Blessing")
@Types({Type.ENCHANTMENT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class BuildersBlessing extends Card
{
	public static final class BuildersBlessingAbility0 extends StaticAbility
	{
		public BuildersBlessingAbility0(GameState state)
		{
			super(state, "Untapped creatures you control get +0/+2.");

			SetGenerator who = Intersect.instance(Untapped.instance(), CREATURES_YOU_CONTROL);
			this.addEffectPart(modifyPowerAndToughness(who, +0, +2));
		}
	}

	public BuildersBlessing(GameState state)
	{
		super(state);

		// Untapped creatures you control get +0/+2.
		this.addAbility(new BuildersBlessingAbility0(state));
	}
}
