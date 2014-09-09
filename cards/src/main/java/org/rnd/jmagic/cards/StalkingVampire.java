package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Stalking Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ColorIdentity({Color.BLACK})
public final class StalkingVampire extends AlternateCard
{
	public static final class StalkingVampireAbility0 extends EventTriggeredAbility
	{
		public StalkingVampireAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may pay (2)(B)(B). If you do, transform Stalking Vampire.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(ifThen(youMayPay("(2)(B)(B)"), transformThis("Stalking Vampire"), "You may pay (2)(B)(B). If you do, transform Stalking Vampire."));
		}
	}

	public StalkingVampire(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.setColorIndicator(Color.BLACK);

		// At the beginning of your upkeep, you may pay (2)(B)(B). If you do,
		// transform Stalking Vampire.
		this.addAbility(new StalkingVampireAbility0(state));
	}
}
