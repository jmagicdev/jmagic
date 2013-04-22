package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sensor Splicer")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SensorSplicer extends Card
{
	public static final class SensorSplicerAbility0 extends EventTriggeredAbility
	{
		public SensorSplicerAbility0(GameState state)
		{
			super(state, "When Sensor Splicer enters the battlefield, put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			token.setSubTypes(SubType.GOLEM);
			token.setArtifact();
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class SensorSplicerAbility1 extends StaticAbility
	{
		public SensorSplicerAbility1(GameState state)
		{
			super(state, "Golem creatures you control have vigilance.");

			SetGenerator yourGolems = Intersect.instance(HasSubType.instance(SubType.GOLEM), CREATURES_YOU_CONTROL);
			this.addEffectPart(addAbilityToObject(yourGolems, org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public SensorSplicer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Sensor Splicer enters the battlefield, put a 3/3 colorless Golem
		// artifact creature token onto the battlefield.
		this.addAbility(new SensorSplicerAbility0(state));

		// Golem creatures you control have vigilance.
		this.addAbility(new SensorSplicerAbility1(state));
	}
}
