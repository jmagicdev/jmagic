package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Caldera Hellion")
@Types({Type.CREATURE})
@SubTypes({SubType.HELLION})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class CalderaHellion extends Card
{
	public static final class ETBNuke extends EventTriggeredAbility
	{
		public ETBNuke(GameState state)
		{
			super(state, "When Caldera Hellion enters the battlefield, it deals 3 damage to each creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(permanentDealDamage(3, CreaturePermanents.instance(), "Caldera Hellion deals 3 damage to each creature."));
		}
	}

	public CalderaHellion(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Devour 1
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Devour(state, 1));

		// When Caldera Hellion enters the battlefield, it deals 3 damage to
		// each creature.
		this.addAbility(new ETBNuke(state));
	}
}
