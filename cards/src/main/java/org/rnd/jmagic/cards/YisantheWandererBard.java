package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Yisan, the Wanderer Bard")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class YisantheWandererBard extends Card
{
	public static final class YisantheWandererBardAbility0 extends ActivatedAbility
	{
		public YisantheWandererBardAbility0(GameState state)
		{
			super(state, "(2)(G), (T), Put a verse counter on Yisan, the Wanderer Bard: Search your library for a creature card with converted mana cost equal to the number of verse counters on Yisan, put it onto the battlefield, then shuffle your library.");
			this.setManaCost(new ManaPool("(2)(G)"));
			this.costsTap = true;
			this.addCost(putCountersOnThis(1, Counter.CounterType.VERSE, "Put a verse counter on Yisan, the Wanderer Bard"));

			SetGenerator counters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.VERSE));
			SetGenerator cheapEnough = HasConvertedManaCost.instance(Between.instance(null, counters));
			SetGenerator condition = Intersect.instance(HasType.instance(Type.CREATURE), cheapEnough);

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a creature card with converted mana cost equal to the number of verse counters on Yisan, put it onto the battlefield, then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(condition));
			this.addEffect(search);
		}
	}

	public YisantheWandererBard(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// (2)(G), (T), Put a verse counter on Yisan, the Wanderer Bard: Search
		// your library for a creature card with converted mana cost equal to
		// the number of verse counters on Yisan, put it onto the battlefield,
		// then shuffle your library.
		this.addAbility(new YisantheWandererBardAbility0(state));
	}
}
