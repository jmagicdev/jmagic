package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Master Splicer")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class MasterSplicer extends Card
{
	public static final class MasterSplicerAbility0 extends EventTriggeredAbility
	{
		public MasterSplicerAbility0(GameState state)
		{
			super(state, "When Master Splicer enters the battlefield, put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			token.setSubTypes(SubType.GOLEM);
			token.setArtifact();
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class MasterSplicerAbility1 extends StaticAbility
	{
		public MasterSplicerAbility1(GameState state)
		{
			super(state, "Golem creatures you control get +1/+1.");

			SetGenerator yourGolems = Intersect.instance(HasSubType.instance(SubType.GOLEM), CREATURES_YOU_CONTROL);
			this.addEffectPart(modifyPowerAndToughness(yourGolems, +1, +1));
		}
	}

	public MasterSplicer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Master Splicer enters the battlefield, put a 3/3 colorless Golem
		// artifact creature token onto the battlefield.
		this.addAbility(new MasterSplicerAbility0(state));

		// Golem creatures you control get +1/+1.
		this.addAbility(new MasterSplicerAbility1(state));
	}
}
