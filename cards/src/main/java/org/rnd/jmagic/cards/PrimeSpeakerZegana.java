package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Prime Speaker Zegana")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.WIZARD})
@ManaCost("2GGUU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class PrimeSpeakerZegana extends Card
{
	public static final class PrimeSpeakerZeganaAbility1 extends EventTriggeredAbility
	{
		public PrimeSpeakerZeganaAbility1(GameState state)
		{
			super(state, "When Prime Speaker Zegana enters the battlefield, draw cards equal to its power.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator amount = PowerOf.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(drawCards(You.instance(), amount, "Draw cards equal to its power."));
		}
	}

	public PrimeSpeakerZegana(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Prime Speaker Zegana enters the battlefield with X +1/+1 counters on
		// it, where X is the greatest power among other creatures you control.
		SetGenerator amount = Maximum.instance(PowerOf.instance(CREATURES_YOU_CONTROL));
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Prime Speaker Zegana", amount, "X +1/+1 counters on it, where X is the greatest power among other creatures you control", Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// When Prime Speaker Zegana enters the battlefield, draw cards equal to
		// its power.
		this.addAbility(new PrimeSpeakerZeganaAbility1(state));
	}
}
