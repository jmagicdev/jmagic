package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sigil Captain")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.RHINO})
@ManaCost("1GWW")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = AlaraReborn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SigilCaptain extends Card
{
	public static final class SigilTraining extends EventTriggeredAbility
	{
		public SigilTraining(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under your control, if that creature is 1/1, put two +1/+1 counters on it.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), CreaturePermanents.instance(), You.instance(), false));

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			this.interveningIf = Both.instance(Intersect.instance(numberGenerator(1), PowerOf.instance(thatCreature)), Intersect.instance(numberGenerator(1), ToughnessOf.instance(thatCreature)));

			EventFactory factory = putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, thatCreature, "Put two +1/+1 counters on it");
			this.addEffect(factory);
		}
	}

	public SigilCaptain(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new SigilTraining(state));
	}
}
