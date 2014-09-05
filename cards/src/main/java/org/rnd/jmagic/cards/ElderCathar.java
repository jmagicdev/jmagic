package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Elder Cathar")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ElderCathar extends Card
{
	public static final class ElderCatharAbility0 extends EventTriggeredAbility
	{
		public ElderCatharAbility0(GameState state)
		{
			super(state, "When Elder Cathar dies, put a +1/+1 counter on target creature you control. If that creature is a Human, put two +1/+1 counters on it instead.");
			this.addPattern(whenThisDies());

			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			SetGenerator ifHuman = IfThenElse.instance(Intersect.instance(target, HasSubType.instance(SubType.HUMAN)), numberGenerator(2), numberGenerator(1));
			this.addEffect(putCounters(ifHuman, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature you control. If that creature is a Human, put two +1/+1 counters on it instead."));
		}
	}

	public ElderCathar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Elder Cathar dies, put a +1/+1 counter on target creature you
		// control. If that creature is a Human, put two +1/+1 counters on it
		// instead.
		this.addAbility(new ElderCatharAbility0(state));
	}
}
