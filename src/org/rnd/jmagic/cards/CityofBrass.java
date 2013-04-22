package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("City of Brass")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ARABIAN_NIGHTS, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class CityofBrass extends Card
{
	public static final class CityofBrassAbility0 extends EventTriggeredAbility
	{
		public CityofBrassAbility0(GameState state)
		{
			super(state, "Whenever City of Brass becomes tapped, it deals 1 damage to you.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.TAP_ONE_PERMANENT);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			this.addEffect(permanentDealDamage(1, You.instance(), "City of Brass deals 1 damage to you."));
		}
	}

	public CityofBrass(GameState state)
	{
		super(state);

		// Whenever City of Brass becomes tapped, it deals 1 damage to you.
		this.addAbility(new CityofBrassAbility0(state));

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}
