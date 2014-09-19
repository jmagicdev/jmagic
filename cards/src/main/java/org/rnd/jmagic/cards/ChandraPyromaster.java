package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chandra, Pyromaster")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.CHANDRA})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class ChandraPyromaster extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("ChandraPyromaster", "Choose an instant or sorcery card exiled this way.", true);

	public static final class ChandraPyromasterAbility0 extends LoyaltyAbility
	{
		public ChandraPyromasterAbility0(GameState state)
		{
			super(state, +1, "Chandra, Pyromaster deals 1 damage to target player and 1 damage to up to one target creature that player controls. That creature can't block this turn.");

			SetGenerator targetPlayer = targetedBy(this.addTarget(Players.instance(), "target player"));

			SetGenerator legal = Intersect.instance(ControlledBy.instance(targetPlayer), CreaturePermanents.instance());
			Target creature = this.addTarget(legal, "up to one target creature that player controls");
			creature.setNumber(0, 1);
			SetGenerator targetCreature = targetedBy(creature);

			this.addEffect(permanentDealDamage(1, Union.instance(targetPlayer, targetCreature), "Chandra, Pyromaster deals 1 damage to target player and 1 damage to up to one target creature that player controls."));

			SetGenerator restriction = Intersect.instance(Blocking.instance(), targetCreature);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffect(createFloatingEffect("That creature can't block this turn.", part));
		}
	}

	public static final class ChandraPyromasterAbility1 extends LoyaltyAbility
	{
		public ChandraPyromasterAbility1(GameState state)
		{
			super(state, 0, "Exile the top card of your library. You may play it this turn.");

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));
			EventFactory exile = exile(topCard, "Exile the top card of your library.");
			this.addEffect(exile);

			PlayPermission permission = new PlayPermission(You.instance());
			SetGenerator thatCard = NewObjectOf.instance(EffectResult.instance(exile));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCard);
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(permission));
			this.addEffect(createFloatingEffect("You may play it this turn.", part));
		}
	}

	public static final class ChandraPyromasterAbility2 extends LoyaltyAbility
	{
		public ChandraPyromasterAbility2(GameState state)
		{
			super(state, -7, "Exile the top ten cards of your library. Choose an instant or sorcery card exiled this way and copy it three times. You may cast the copies without paying their mana costs.");

			SetGenerator topTen = TopCards.instance(10, LibraryOf.instance(You.instance()));
			EventFactory exile = exile(topTen, "Exile the top ten cards of your library.");
			this.addEffect(exile);

			SetGenerator choices = Intersect.instance(NewObjectOf.instance(EffectResult.instance(exile)), HasType.instance(Type.INSTANT, Type.SORCERY));
			EventFactory choose = playerChoose(You.instance(), 1, choices, PlayerInterface.ChoiceType.OBJECTS, REASON, "Choose an instant or sorcery card exiled this way");
			this.addEffect(choose);

			EventFactory copy = new EventFactory(EventType.COPY_SPELL_CARD, "and copy it three times.");
			copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copy.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(choose));
			copy.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(copy);

			SetGenerator copies = NewObjectOf.instance(EffectResult.instance(copy));
			EventFactory castTheCopies = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast the copies without paying their mana costs.");
			castTheCopies.parameters.put(EventType.Parameter.CAUSE, This.instance());
			castTheCopies.parameters.put(EventType.Parameter.PLAYER, You.instance());
			castTheCopies.parameters.put(EventType.Parameter.OBJECT, copies);
			this.addEffect(castTheCopies);
		}
	}

	public ChandraPyromaster(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Chandra, Pyromaster deals 1 damage to target player and 1 damage
		// to up to one target creature that player controls. That creature
		// can't block this turn.
		this.addAbility(new ChandraPyromasterAbility0(state));

		// 0: Exile the top card of your library. You may play it this turn.
		this.addAbility(new ChandraPyromasterAbility1(state));

		// -7: Exile the top ten cards of your library. Choose an instant or
		// sorcery card exiled this way and copy it three times. You may cast
		// the copies without paying their mana costs.
		this.addAbility(new ChandraPyromasterAbility2(state));
	}
}
