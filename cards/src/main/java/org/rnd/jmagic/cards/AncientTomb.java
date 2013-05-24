package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ancient Tomb")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class AncientTomb extends Card
{
	public static final class AncientTombAbility0 extends ActivatedAbility
	{
		public AncientTombAbility0(GameState state)
		{
			super(state, "(T): Add (2) to your mana pool. Ancient Tomb deals 2 damage to you.");
			this.costsTap = true;
			this.addEffect(addManaToYourManaPoolFromAbility("(2)"));
			this.addEffect(permanentDealDamage(2, You.instance(), "Ancient Tomb deals 2 damage to you."));
		}
	}

	public AncientTomb(GameState state)
	{
		super(state);

		// (T): Add (2) to your mana pool. Ancient Tomb deals 2 damage to you.
		this.addAbility(new AncientTombAbility0(state));
	}
}
