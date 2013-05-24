package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Liliana of the Dark Realms")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.LILIANA})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class LilianaoftheDarkRealms extends Card
{
	public static final String PLUS_X_PLUS_X = "+X/+X";
	public static final String MINUS_X_MINUS_X = "-X/-X";
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("LilianaoftheDarkRealms", "Choose +X/+X or -X/-X.", true);

	public static final class LilianaoftheDarkRealmsAbility0 extends LoyaltyAbility
	{
		public LilianaoftheDarkRealmsAbility0(GameState state)
		{
			super(state, +1, "Search your library for a Swamp card, reveal it, and put it into your hand. Then shuffle your library.");

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Swamp card, reveal it, put it into your hand, then shuffle your library");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.SWAMP)));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(search);
		}
	}

	public static final class LilianaoftheDarkRealmsAbility1 extends LoyaltyAbility
	{
		public LilianaoftheDarkRealmsAbility1(GameState state)
		{
			super(state, -3, "Target creature gets +X/+X or -X/-X until end of turn, where X is the number of Swamps you control.");

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			SetGenerator X = Count.instance(Intersect.instance(HasSubType.instance(SubType.SWAMP), ControlledBy.instance(You.instance())));
			EventFactory choose = playerChoose(You.instance(), 1, Identity.instance(PLUS_X_PLUS_X, MINUS_X_MINUS_X), PlayerInterface.ChoiceType.STRING, REASON, "Choose +X/+X or -X/-X.");
			this.addEffect(choose);

			SetGenerator number = IfThenElse.instance(Intersect.instance(Identity.instance(PLUS_X_PLUS_X), EffectResult.instance(choose)), X, Subtract.instance(numberGenerator(0), X));
			this.addEffect(ptChangeUntilEndOfTurn(target, number, number, "Target creature gets +X/+X or -X/-X until end of turn, where X is the number of Swamps you control."));
		}
	}

	public static final class LilianaoftheDarkRealmsAbility2 extends LoyaltyAbility
	{
		public static final class SuperSwampGrant extends StaticAbility
		{
			public static final class SuperSwamps extends org.rnd.jmagic.abilities.TapForMana
			{
				public SuperSwamps(GameState state)
				{
					super(state, "(B)(B)(B)(B)");
				}
			}

			public SuperSwampGrant(GameState state)
			{
				super(state, "Swamps you control have \"(T): Add (B)(B)(B)(B) to your mana pool.\"");

				this.addEffectPart(addAbilityToObject(Intersect.instance(HasSubType.instance(SubType.SWAMP), ControlledBy.instance(You.instance())), new SimpleAbilityFactory(SuperSwamps.class)));
			}
		}

		public LilianaoftheDarkRealmsAbility2(GameState state)
		{
			super(state, -6, "You get an emblem with \"Swamps you control have '(T): Add (B)(B)(B)(B) to your mana pool.'\"");

			EventFactory makeEmblem = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"Swamps you control have '(T): Add (B)(B)(B)(B) to your mana pool.\"");
			makeEmblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeEmblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(SuperSwampGrant.class));
			makeEmblem.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(makeEmblem);
		}
	}

	public LilianaoftheDarkRealms(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +1: Search your library for a Swamp card, reveal it, and put it into
		// your hand. Then shuffle your library.
		this.addAbility(new LilianaoftheDarkRealmsAbility0(state));

		// -3: Target creature gets +X/+X or -X/-X until end of turn, where X is
		// the number of Swamps you control.
		this.addAbility(new LilianaoftheDarkRealmsAbility1(state));

		// -6: You get an emblem with
		// "Swamps you control have '(T): Add (B)(B)(B)(B) to your mana pool.'"
		this.addAbility(new LilianaoftheDarkRealmsAbility2(state));
	}
}
