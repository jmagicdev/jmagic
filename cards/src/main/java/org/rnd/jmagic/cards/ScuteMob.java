package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scute Mob")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class ScuteMob extends Card
{
	public static final class MobIsBig extends EventTriggeredAbility
	{
		public MobIsBig(GameState state)
		{
			super(state, "At the beginning of your upkeep, if you control five or more lands, put four +1/+1 counters on Scute Mob.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator yourLands = Intersect.instance(HasType.instance(Type.LAND), ControlledBy.instance(You.instance()));
			this.interveningIf = Intersect.instance(Count.instance(yourLands), Between.instance(5, null));

			this.addEffect(putCounters(4, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put four +1/+1 counters on Scute Mob."));
		}
	}

	public ScuteMob(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// At the beginning of your upkeep, if you control five or more lands,
		// put four +1/+1 counters on Scute Mob.
		this.addAbility(new MobIsBig(state));
	}
}
