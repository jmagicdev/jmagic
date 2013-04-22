package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Favorable Winds")
@Types({Type.ENCHANTMENT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class FavorableWinds extends Card
{
	public static final class FavorableWindsAbility0 extends StaticAbility
	{
		public FavorableWindsAbility0(GameState state)
		{
			super(state, "Creatures you control with flying get +1/+1.");

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffectPart(modifyPowerAndToughness(Intersect.instance(CREATURES_YOU_CONTROL, hasFlying), +1, +1));
		}
	}

	public FavorableWinds(GameState state)
	{
		super(state);

		// Creatures you control with flying get +1/+1.
		this.addAbility(new FavorableWindsAbility0(state));
	}
}
