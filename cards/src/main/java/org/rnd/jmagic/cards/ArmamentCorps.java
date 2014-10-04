package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Armament Corps")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("2WBG")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class ArmamentCorps extends Card
{
	public static final class ArmamentCorpsAbility0 extends EventTriggeredAbility
	{
		public ArmamentCorpsAbility0(GameState state)
		{
			super(state, "When Armament Corps enters the battlefield, distribute two +1/+1 counters among one or two target creatures you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedDistribute(this.addTarget(CREATURES_YOU_CONTROL, "one or two target creatures you control").setNumber(1, 2));
			this.setDivision(Union.instance(numberGenerator(2), Identity.instance("+1/+1 counters")));

			EventFactory effect = new EventFactory(EventType.DISTRIBUTE_COUNTERS, "Distribute two +1/+1 counters among one or two target creatures you control.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.OBJECT, target);
			effect.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addEffect(effect);
		}
	}

	public ArmamentCorps(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Armament Corps enters the battlefield, distribute two +1/+1
		// counters among one or two target creatures you control.
		this.addAbility(new ArmamentCorpsAbility0(state));
	}
}
