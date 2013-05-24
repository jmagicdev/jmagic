package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Zombie Infestation")
@Types({Type.ENCHANTMENT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class ZombieInfestation extends Card
{
	public static final class ZombieInfestationAbility0 extends ActivatedAbility
	{
		public ZombieInfestationAbility0(GameState state)
		{
			super(state, "Discard two cards: Put a 2/2 black Zombie creature token onto the battlefield.");
			// Discard two cards

			EventFactory discard = discardCards(You.instance(), 2, "Discard two cards");
			this.addCost(discard);

			CreateTokensFactory tokens = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
			tokens.setColors(Color.BLACK);
			tokens.setSubTypes(SubType.ZOMBIE);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public ZombieInfestation(GameState state)
	{
		super(state);

		// Discard two cards: Put a 2/2 black Zombie creature token onto the
		// battlefield.
		this.addAbility(new ZombieInfestationAbility0(state));
	}
}
