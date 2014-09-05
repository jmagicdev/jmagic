package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Defense of the Heart")
@Types({Type.ENCHANTMENT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = UrzasLegacy.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class DefenseoftheHeart extends Card
{
	public static final class DefenseoftheHeartAbility0 extends EventTriggeredAbility
	{
		public DefenseoftheHeartAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, if an opponent controls three or more creatures, sacrifice Defense of the Heart, search your library for up to two creature cards, and put those cards onto the battlefield. Then shuffle your library.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			DynamicEvaluation dynamic = DynamicEvaluation.instance();
			SetGenerator count = Count.instance(Intersect.instance(ControlledBy.instance(dynamic), CreaturePermanents.instance()));
			this.interveningIf = Intersect.instance(Between.instance(3, null), Maximum.instance(ForEach.instance(OpponentsOf.instance(You.instance()), count, dynamic)));

			this.addEffect(sacrificeThis("Defense of the Heart"));

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to two creature cards, and put those cards onto the battlefield. Then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 2));
			search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.CREATURE)));
			this.addEffect(search);
		}
	}

	public DefenseoftheHeart(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, if an opponent controls three or
		// more creatures, sacrifice Defense of the Heart, search your library
		// for up to two creature cards, and put those cards onto the
		// battlefield. Then shuffle your library.
		this.addAbility(new DefenseoftheHeartAbility0(state));
	}
}
