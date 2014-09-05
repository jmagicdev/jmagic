package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Jar of Eyeballs")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class JarofEyeballs extends Card
{
	public static final class JarofEyeballsAbility0 extends EventTriggeredAbility
	{
		public JarofEyeballsAbility0(GameState state)
		{
			super(state, "Whenever a creature you control dies, put two eyeball counters on Jar of Eyeballs.");
			this.addPattern(whenXDies(CREATURES_YOU_CONTROL));
			this.addEffect(putCounters(2, Counter.CounterType.EYEBALL, ABILITY_SOURCE_OF_THIS, "Put two eyeball counters on Jar of Eyeballs."));
		}
	}

	public static final class JarofEyeballsAbility1 extends ActivatedAbility
	{
		public JarofEyeballsAbility1(GameState state)
		{
			super(state, "(3), (T), Remove all eyeball counters from Jar of Eyeballs: Look at the top X cards of your library, where X is the number of eyeball counters removed this way. Put one of them into your hand and the rest on the bottom of your library in any order.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;
			// Remove all eyeball counters from Jar of Eyeballs
			EventFactory cost = new EventFactory(EventType.REMOVE_COUNTERS, "Remove all eyeball counters from Jar of Eyeballs");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.EYEBALL));
			cost.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addCost(cost);

			EventFactory factory = new EventFactory(LOOK_AT_THE_TOP_N_CARDS_PUT_ONE_INTO_HAND_AND_THE_REST_ON_BOTTOM, "Look at the top X cards of your library, where X is the number of eyeball counters removed this way. Put one of them into your hand and the rest on the bottom of your library in any order.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, Count.instance(CostResult.instance(cost)));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.ZONE, LibraryOf.instance(You.instance()));
			this.addEffect(factory);
		}
	}

	public JarofEyeballs(GameState state)
	{
		super(state);

		// Whenever a creature you control dies, put two eyeball counters on Jar
		// of Eyeballs.
		this.addAbility(new JarofEyeballsAbility0(state));

		// (3), (T), Remove all eyeball counters from Jar of Eyeballs: Look at
		// the top X cards of your library, where X is the number of eyeball
		// counters removed this way. Put one of them into your hand and the
		// rest on the bottom of your library in any order.
		this.addAbility(new JarofEyeballsAbility1(state));
	}
}
