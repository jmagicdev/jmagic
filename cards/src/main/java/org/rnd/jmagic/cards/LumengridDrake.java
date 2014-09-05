package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lumengrid Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class LumengridDrake extends Card
{
	public static final class LumengridDrakeAbility1 extends EventTriggeredAbility
	{
		public LumengridDrakeAbility1(GameState state)
		{
			super(state, "When Lumengrid Drake enters the battlefield, if you control three or more artifacts, return target creature to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = Metalcraft.instance();
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(bounce(targetedBy(target), "Return target creature to its owner's hand."));
		}
	}

	public LumengridDrake(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Metalcraft \u2014 When Lumengrid Drake enters the battlefield, if you
		// control three or more artifacts, return target creature to its
		// owner's hand.
		this.addAbility(new LumengridDrakeAbility1(state));
	}
}
