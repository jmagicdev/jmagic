package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Broodmate Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("3BRG")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class BroodmateDragon extends Card
{
	public static final class MakeMate extends EventTriggeredAbility
	{
		public MakeMate(GameState state)
		{
			super(state, "When Broodmate Dragon enters the battlefield, put a 4/4 red Dragon creature token with flying onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 4, 4, "Put a 4/4 red Dragon creature token with flying onto the battlefield.");
			token.setColors(Color.RED);
			token.setSubTypes(SubType.DRAGON);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public BroodmateDragon(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Broodmate Dragon enters the battlefield, put a 4/4 red Dragon
		// creature token with flying onto the battlefield.
		this.addAbility(new MakeMate(state));
	}
}
