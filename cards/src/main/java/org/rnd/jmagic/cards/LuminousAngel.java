package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Luminous Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4WWW")
@ColorIdentity({Color.WHITE})
public final class LuminousAngel extends Card
{
	public static final class SpiritBabies extends EventTriggeredAbility
	{
		public SpiritBabies(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may put a 1/1 white Spirit creature token with flying onto the battlefield.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Spirit creature token with flying onto the battlefield");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.SPIRIT);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(youMay(token.getEventFactory(), "You may put a 1/1 white Spirit creature token with flying onto the battlefield."));
		}
	}

	public LuminousAngel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, you may put a 1/1 white Spirit
		// creature token with flying onto the battlefield.
		this.addAbility(new SpiritBabies(state));
	}
}
