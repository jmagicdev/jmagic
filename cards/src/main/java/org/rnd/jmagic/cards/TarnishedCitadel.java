package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tarnished Citadel")
@Types({Type.LAND})
@ColorIdentity({})
public final class TarnishedCitadel extends Card
{
	public static final class TarnishedCitadelAbility1 extends ActivatedAbility
	{
		public TarnishedCitadelAbility1(GameState state)
		{
			super(state, "(T): Add one mana of any color to your mana pool. Tarnished Citadel deals 3 damage to you.");

			this.costsTap = true;

			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));

			this.addEffect(permanentDealDamage(3, You.instance(), "Tarnished Citadel deals 3 damage to you."));
		}
	}

	public TarnishedCitadel(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T): Add one mana of any color to your mana pool. Tarnished Citadel
		// deals 3 damage to you.
		this.addAbility(new TarnishedCitadelAbility1(state));
	}
}
