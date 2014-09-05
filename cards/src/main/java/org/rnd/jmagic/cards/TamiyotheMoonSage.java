package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Tamiyo, the Moon Sage")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.TAMIYO})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class TamiyotheMoonSage extends Card
{
	public static final class TamiyotheMoonSageAbility0 extends LoyaltyAbility
	{
		public TamiyotheMoonSageAbility0(GameState state)
		{
			super(state, +1, "Tap target permanent. It doesn't untap during its controller's next untap step.");

			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
			EventFactory tapHard = new EventFactory(EventType.TAP_HARD, "Tap target permanent. It doesn't untap during its controller's next untap step.");
			tapHard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapHard.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(tapHard);
		}
	}

	public static final class TamiyotheMoonSageAbility1 extends LoyaltyAbility
	{
		public TamiyotheMoonSageAbility1(GameState state)
		{
			super(state, -2, "Draw a card for each tapped creature target player controls.");

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			SetGenerator number = Count.instance(Intersect.instance(Tapped.instance(), CreaturePermanents.instance(), ControlledBy.instance(target)));
			this.addEffect(drawCards(You.instance(), number, "Draw a card for each tapped creature target player controls."));
		}
	}

	public static final class TamiyotheMoonSageAbility2 extends LoyaltyAbility
	{
		public static final class NoMaxHandSize extends StaticAbility
		{
			public NoMaxHandSize(GameState state)
			{
				super(state, "You have no maximum hand size");

				ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_MAX_HAND_SIZE);
				part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
				part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Empty.instance());
				this.addEffectPart(part);
			}
		}

		public static final class AntiGraveyard extends EventTriggeredAbility
		{
			public AntiGraveyard(GameState state)
			{
				super(state, "Whenever a card is put into your graveyard from anywhere, you may return it to your hand.");
				this.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(You.instance()), Cards.instance(), false));

				SetGenerator what = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
				this.addEffect(youMay(putIntoHand(what, You.instance(), "Return it to your hand")));
			}
		}

		public TamiyotheMoonSageAbility2(GameState state)
		{
			super(state, -8, "You get an emblem with \"You have no maximum hand size\" and \"Whenever a card is put into your graveyard from anywhere, you may return it to your hand.\"");

			EventFactory makeEmblem = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"You have no maximum hand size\" and \"Whenever a card is put into your graveyard from anywhere, you may return it to your hand.\"");
			makeEmblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeEmblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(NoMaxHandSize.class, AntiGraveyard.class));
			makeEmblem.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(makeEmblem);
		}
	}

	public TamiyotheMoonSage(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Tap target permanent. It doesn't untap during its controller's
		// next untap step.
		this.addAbility(new TamiyotheMoonSageAbility0(state));

		// -2: Draw a card for each tapped creature target player controls.
		this.addAbility(new TamiyotheMoonSageAbility1(state));

		// -8: You get an emblem with "You have no maximum hand size" and
		// "Whenever a card is put into your graveyard from anywhere, you may return it to your hand."
		this.addAbility(new TamiyotheMoonSageAbility2(state));
	}
}
