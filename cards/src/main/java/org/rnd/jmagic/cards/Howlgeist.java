package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Howlgeist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.WOLF})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class Howlgeist extends Card
{
	public static final class HowlgeistAbility0 extends StaticAbility
	{
		public HowlgeistAbility0(GameState state)
		{
			super(state, "Creatures with power less than Howlgeist's power can't block it.");

			SetGenerator thisPower = PowerOf.instance(This.instance());
			SetGenerator lessThanThisPower = Between.instance(null, Subtract.instance(thisPower, numberGenerator(1)));
			SetGenerator restriction = Intersect.instance(HasPower.instance(lessThanThisPower), Blocking.instance(This.instance()));
			ContinuousEffect.Part cantBlock = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			cantBlock.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(cantBlock);
		}
	}

	public Howlgeist(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Creatures with power less than Howlgeist's power can't block it.
		this.addAbility(new HowlgeistAbility0(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
