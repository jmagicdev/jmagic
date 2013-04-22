package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Village Cannibals")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class VillageCannibals extends Card
{
	public static final class VillageCannibalsAbility0 extends EventTriggeredAbility
	{
		public VillageCannibalsAbility0(GameState state)
		{
			super(state, "Whenever another Human creature dies, put a +1/+1 counter on Village Cannibals.");
			this.addPattern(whenXDies(RelativeComplement.instance(Intersect.instance(HasSubType.instance(SubType.HUMAN), HasType.instance(Type.CREATURE)), ABILITY_SOURCE_OF_THIS)));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Village Cannibals."));
		}
	}

	public VillageCannibals(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever another Human creature dies, put a +1/+1 counter on Village
		// Cannibals.
		this.addAbility(new VillageCannibalsAbility0(state));
	}
}
