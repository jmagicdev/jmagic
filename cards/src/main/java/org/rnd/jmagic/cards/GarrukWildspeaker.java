package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Garruk Wildspeaker")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.GARRUK})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class GarrukWildspeaker extends Card
{
	public static final class UntapLands extends LoyaltyAbility
	{
		public UntapLands(GameState state)
		{
			super(state, +1, "Untap two target lands.");

			Target t = this.addTarget(LandPermanents.instance(), "two target lands");
			t.setSingleNumber(numberGenerator(2));
			this.addEffect(untap(targetedBy(t), "Untap two target lands."));
		}
	}

	public static final class MakeBeast extends LoyaltyAbility
	{
		public MakeBeast(GameState state)
		{
			super(state, -1, "Put a 3/3 green Beast creature token onto the battlefield.");
			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Beast creature token onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.BEAST);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class GarrukOverrun extends LoyaltyAbility
	{
		public GarrukOverrun(GameState state)
		{
			super(state, -4, "Creatures you control get +3/+3 and gain trample until end of turn.");

			String effectName = "Creatures you control get +3/+3 and gain trample until end of turn.";
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +3, +3, effectName, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public GarrukWildspeaker(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		this.addAbility(new UntapLands(state));
		this.addAbility(new MakeBeast(state));
		this.addAbility(new GarrukOverrun(state));
	}
}
