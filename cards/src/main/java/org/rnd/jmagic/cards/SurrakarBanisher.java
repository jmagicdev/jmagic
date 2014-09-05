package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Surrakar Banisher")
@Types({Type.CREATURE})
@SubTypes({SubType.SURRAKAR})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SurrakarBanisher extends Card
{
	public static final class SurrakarBanisherAbility0 extends EventTriggeredAbility
	{
		public SurrakarBanisherAbility0(GameState state)
		{
			super(state, "When Surrakar Banisher enters the battlefield, you may return target tapped creature to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator tappedCreatures = Intersect.instance(Tapped.instance(), CreaturePermanents.instance());
			SetGenerator t = targetedBy(this.addTarget(tappedCreatures, "target tapped creature"));
			this.addEffect(youMay(bounce(t, "Return target tapped creature to its owner's hand."), "You may return target tapped creature to its owner's hand."));
		}
	}

	public SurrakarBanisher(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Surrakar Banisher enters the battlefield, you may return target
		// tapped creature to its owner's hand.
		this.addAbility(new SurrakarBanisherAbility0(state));
	}
}
