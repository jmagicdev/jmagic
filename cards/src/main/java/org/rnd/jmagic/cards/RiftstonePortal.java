package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Riftstone Portal")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class RiftstonePortal extends Card
{
	public static final class RiftstonePortalAbility1 extends StaticAbility
	{
		public static final class TapForGW extends org.rnd.jmagic.abilities.TapForMana
		{
			public TapForGW(GameState state)
			{
				super(state, "(GW)");
			}
		}

		public RiftstonePortalAbility1(GameState state)
		{
			super(state, "As long as Riftstone Portal is in your graveyard, lands you control have \"(T): Add (G) or (W) to your mana pool.\"");

			this.addEffectPart(addAbilityToObject(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())), TapForGW.class));

			this.canApply = THIS_IS_IN_A_GRAVEYARD;
		}
	}

	public RiftstonePortal(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// As long as Riftstone Portal is in your graveyard, lands you control
		// have "(T): Add (G) or (W) to your mana pool."
		this.addAbility(new RiftstonePortalAbility1(state));
	}
}
