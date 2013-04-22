package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vital Splicer")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class VitalSplicer extends Card
{
	public static final class VitalSplicerAbility0 extends EventTriggeredAbility
	{
		public VitalSplicerAbility0(GameState state)
		{
			super(state, "When Vital Splicer enters the battlefield, put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			token.setSubTypes(SubType.GOLEM);
			token.setArtifact();
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class VitalSplicerAbility1 extends ActivatedAbility
	{
		public VitalSplicerAbility1(GameState state)
		{
			super(state, "(1): Regenerate target Golem you control.");
			this.setManaCost(new ManaPool("(1)"));

			SetGenerator golem = Intersect.instance(HasSubType.instance(SubType.GOLEM), ControlledBy.instance(You.instance()));
			SetGenerator target = targetedBy(this.addTarget(golem, "target Golem you control"));
			this.addEffect(regenerate(target, "Regenerate target Golem you control."));
		}
	}

	public VitalSplicer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Vital Splicer enters the battlefield, put a 3/3 colorless Golem
		// artifact creature token onto the battlefield.
		this.addAbility(new VitalSplicerAbility0(state));

		// (1): Regenerate target Golem you control.
		this.addAbility(new VitalSplicerAbility1(state));
	}
}
