package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pride of the Clouds")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.ELEMENTAL})
@ManaCost("WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class PrideoftheClouds extends Card
{
	public static final class PrideoftheCloudsAbility1 extends StaticAbility
	{
		public PrideoftheCloudsAbility1(GameState state)
		{
			super(state, "Pride of the Clouds gets +1/+1 for each other creature with flying on the battlefield.");

			SetGenerator flying = Count.instance(HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			SetGenerator creaturesWithFlying = Intersect.instance(CreaturePermanents.instance(), flying);
			SetGenerator other = RelativeComplement.instance(creaturesWithFlying, This.instance());
			SetGenerator forEach = Count.instance(other);
			this.addEffectPart(modifyPowerAndToughness(This.instance(), forEach, forEach));
		}
	}

	public static final class PrideoftheCloudsAbility2 extends org.rnd.jmagic.abilities.keywords.Forecast
	{
		public PrideoftheCloudsAbility2(GameState state)
		{
			super(state, "(2)(W)(U), Reveal Pride of the Clouds from your hand: Put a 1/1 white and blue Bird creature token with flying onto the battlefield.");
			this.setManaCost(new ManaPool("(2)(W)(U)"));

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white and blue Bird creature token with flying onto the battlefield.");
			token.setColors(Color.WHITE, Color.BLUE);
			token.setSubTypes(SubType.BIRD);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public PrideoftheClouds(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Pride of the Clouds gets +1/+1 for each other creature with flying on
		// the battlefield.
		this.addAbility(new PrideoftheCloudsAbility1(state));

		// Forecast \u2014 (2)(W)(U), Reveal Pride of the Clouds from your hand:
		// Put a 1/1 white and blue Bird creature token with flying onto the
		// battlefield. (Activate this ability only during your upkeep and only
		// once each turn.)
		this.addAbility(new PrideoftheCloudsAbility2(state));
	}
}
