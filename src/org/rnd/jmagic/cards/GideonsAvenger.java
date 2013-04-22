package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Gideon's Avenger")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class GideonsAvenger extends Card
{
	public static final class GideonsAvengerAbility0 extends EventTriggeredAbility
	{
		public GideonsAvengerAbility0(GameState state)
		{
			super(state, "Whenever a creature an opponent controls becomes tapped, put a +1/+1 counter on Gideon's Avenger.");

			SimpleEventPattern tapped = new SimpleEventPattern(EventType.TAP_ONE_PERMANENT);
			tapped.put(EventType.Parameter.OBJECT, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))));
			this.addPattern(tapped);

			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Gideon's Avenger"));
		}
	}

	public GideonsAvenger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever a creature an opponent controls becomes tapped, put a +1/+1
		// counter on Gideon's Avenger.
		this.addAbility(new GideonsAvengerAbility0(state));
	}
}
