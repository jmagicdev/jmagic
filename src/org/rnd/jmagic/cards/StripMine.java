package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Strip Mine")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ANTIQUITIES, r = Rarity.RARE)})
@ColorIdentity({})
public final class StripMine extends Card
{
	public static final class Strip extends ActivatedAbility
	{
		public Strip(GameState state)
		{
			super(state, "(T), Sacrifice Strip Mine: Destroy target land.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Strip Mine"));

			Target target = this.addTarget(LandPermanents.instance(), "target land");
			this.addEffect(destroy(targetedBy(target), "Destroy target land."));
		}
	}

	public StripMine(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T), Sacrifice Strip Mine: Destroy target land.
		this.addAbility(new Strip(state));
	}
}
