package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Dragon Roost")
@Types({Type.ENCHANTMENT})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class DragonRoost extends Card
{
	public static final class ReallyBigBirdsAndBees extends ActivatedAbility
	{
		public ReallyBigBirdsAndBees(GameState state)
		{
			super(state, "(5)(R)(R): Put a 5/5 red Dragon creature token with flying onto the battlefield.");

			this.setManaCost(new ManaPool("5RR"));

			CreateTokensFactory token = new CreateTokensFactory(1, 5, 5, "Put a 5/5 red Dragon creature token with flying onto the battlefield.");
			token.setColors(Color.RED);
			token.setSubTypes(SubType.DRAGON);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public DragonRoost(GameState state)
	{
		super(state);

		this.addAbility(new ReallyBigBirdsAndBees(state));
	}
}
