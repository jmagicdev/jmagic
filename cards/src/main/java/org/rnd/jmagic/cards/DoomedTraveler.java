package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Doomed Traveler")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class DoomedTraveler extends Card
{
	public static final class DoomedTravelerAbility0 extends EventTriggeredAbility
	{
		public DoomedTravelerAbility0(GameState state)
		{
			super(state, "When Doomed Traveler dies, put a 1/1 white Spirit creature token with flying onto the battlefield.");
			this.addPattern(whenThisDies());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Spirit creature token with flying onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.SPIRIT);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public DoomedTraveler(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Doomed Traveler dies, put a 1/1 white Spirit creature token with
		// flying onto the battlefield.
		this.addAbility(new DoomedTravelerAbility0(state));
	}
}
