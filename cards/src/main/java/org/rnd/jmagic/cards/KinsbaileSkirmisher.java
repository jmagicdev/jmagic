package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kinsbaile Skirmisher")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.KITHKIN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class KinsbaileSkirmisher extends Card
{
	public static final class KinsbaileSkirmisherAbility0 extends EventTriggeredAbility
	{
		public KinsbaileSkirmisherAbility0(GameState state)
		{
			super(state, "When Kinsbaile Skirmisher enters the battlefield, target creature gets +1/+1 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, +1, +1, "Target creature gets +1/+1 until end of turn."));
		}
	}

	public KinsbaileSkirmisher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Kinsbaile Skirmisher enters the battlefield, target creature
		// gets +1/+1 until end of turn.
		this.addAbility(new KinsbaileSkirmisherAbility0(state));
	}
}
