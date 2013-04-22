package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Emeria Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class EmeriaAngel extends Card
{
	public static final class IAmNotASeagull extends EventTriggeredAbility
	{
		public IAmNotASeagull(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may put a 1/1 white Bird creature token with flying onto the battlefield.");
			this.addPattern(landfall());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Bird creature token with flying onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.BIRD);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(youMay(token.getEventFactory(), "You may put a 1/1 white Bird creature token with flying onto the battlefield."));
		}
	}

	public EmeriaAngel(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may put a 1/1 white Bird creature token with flying onto
		// the battlefield.
		this.addAbility(new IAmNotASeagull(state));
	}
}
