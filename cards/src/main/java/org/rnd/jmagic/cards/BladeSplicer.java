package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blade Splicer")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class BladeSplicer extends Card
{
	public static final class BladeSplicerAbility0 extends EventTriggeredAbility
	{
		public BladeSplicerAbility0(GameState state)
		{
			super(state, "When Blade Splicer enters the battlefield, put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			token.setSubTypes(SubType.GOLEM);
			token.setArtifact();
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class BladeSplicerAbility1 extends StaticAbility
	{
		public BladeSplicerAbility1(GameState state)
		{
			super(state, "Golem creatures you control have first strike.");

			SetGenerator yourGolems = Intersect.instance(HasSubType.instance(SubType.GOLEM), CREATURES_YOU_CONTROL);
			this.addEffectPart(addAbilityToObject(yourGolems, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public BladeSplicer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Blade Splicer enters the battlefield, put a 3/3 colorless Golem
		// artifact creature token onto the battlefield.
		this.addAbility(new BladeSplicerAbility0(state));

		// Golem creatures you control have first strike.
		this.addAbility(new BladeSplicerAbility1(state));
	}
}
