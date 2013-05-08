package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Polluted Dead")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class PollutedDead extends Card
{
	public static final class PollutedDeadAbility0 extends EventTriggeredAbility
	{
		public PollutedDeadAbility0(GameState state)
		{
			super(state, "When Polluted Dead dies, destroy target land.");
			this.addPattern(whenThisDies());
			SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
			this.addEffect(destroy(target, "Destroy target land."));
		}
	}

	public PollutedDead(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Polluted Dead dies, destroy target land.
		this.addAbility(new PollutedDeadAbility0(state));
	}
}
