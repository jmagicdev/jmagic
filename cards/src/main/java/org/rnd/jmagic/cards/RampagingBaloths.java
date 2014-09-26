package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Rampaging Baloths")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class RampagingBaloths extends Card
{
	public static final class LandBeast extends EventTriggeredAbility
	{
		public LandBeast(GameState state)
		{
			super(state, "Landfall \u2014 Whenever a land enters the battlefield under your control, you may put a 4/4 green Beast creature token onto the battlefield.");
			this.addPattern(landfall());

			CreateTokensFactory token = new CreateTokensFactory(1, 4, 4, "Put a 4/4 green Beast creature token onto the battlefield");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.BEAST);
			this.addEffect(youMay(token.getEventFactory(), "You may put a 4/4 green Beast creature token onto the battlefield."));
		}
	}

	public RampagingBaloths(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may put a 4/4 green Beast creature token onto the
		// battlefield.
		this.addAbility(new LandBeast(state));
	}
}
