package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Roc Egg")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class RocEgg extends Card
{
	public static final class RocEggAbility1 extends EventTriggeredAbility
	{
		public RocEggAbility1(GameState state)
		{
			super(state, "When Roc Egg dies, put a 3/3 white Bird creature token with flying onto the battlefield.");
			this.addPattern(whenThisDies());

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 white Bird creature token with flying onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.BIRD);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public RocEgg(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// When Roc Egg is put into a graveyard from the battlefield, put a 3/3
		// white Bird creature token with flying onto the battlefield.
		this.addAbility(new RocEggAbility1(state));
	}
}
