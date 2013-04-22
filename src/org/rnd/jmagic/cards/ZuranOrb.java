package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Zuran Orb")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.RELICS, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ZuranOrb extends Card
{
	public static final class ZuranOrbAbility0 extends ActivatedAbility
	{
		public ZuranOrbAbility0(GameState state)
		{
			super(state, "Sacrifice a land: You gain 2 life.");
			// Sacrifice a land
			this.addCost(sacrifice(You.instance(), 1, LandPermanents.instance(), "Sacrifice a land"));

			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public ZuranOrb(GameState state)
	{
		super(state);

		// Sacrifice a land: You gain 2 life.
		this.addAbility(new ZuranOrbAbility0(state));
	}
}
