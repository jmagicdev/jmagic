package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hollowhenge Scavenger")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class HollowhengeScavenger extends Card
{
	public static final class HollowhengeScavengerAbility0 extends EventTriggeredAbility
	{
		public HollowhengeScavengerAbility0(GameState state)
		{
			super(state, "Morbid \u2014 When Hollowhenge Scavenger enters the battlefield, if a creature died this turn, you gain 5 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = Morbid.instance();

			state.ensureTracker(new Morbid.Tracker());
			this.addEffect(gainLife(You.instance(), 5, "You gain 5 life."));
		}
	}

	public HollowhengeScavenger(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Morbid \u2014 When Hollowhenge Scavenger enters the battlefield, if a
		// creature died this turn, you gain 5 life.
		this.addAbility(new HollowhengeScavengerAbility0(state));
	}
}
