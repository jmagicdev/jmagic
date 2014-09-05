package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Chandra Ablaze")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.CHANDRA})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class ChandraAblaze extends Card
{
	/**
	 * @eparam CAUSE: the +1 ability
	 * @eparam SOURCE: chandra
	 * @eparam PLAYER: controller of CAUSE
	 * @eparam CARD: the cards in PLAYER's hand
	 * @eparam TARGET: object/player targetted by CAUSE
	 * @eparam RESULT: empty
	 */
	public static final EventType CHANDRA_DISCARD = new EventType("CHANDRA_DISCARD")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set ability = parameters.get(Parameter.CAUSE);
			Set you = parameters.get(Parameter.PLAYER);
			Set cards = parameters.get(Parameter.CARD);

			java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
			discardParameters.put(Parameter.CAUSE, ability);
			discardParameters.put(Parameter.PLAYER, you);
			discardParameters.put(Parameter.CHOICE, cards);
			Event discard = createEvent(game, "Discard a card", DISCARD_CHOICE, discardParameters);
			discard.perform(event, true);

			Set discarded = NewObjectOf.instance(discard.getResultGenerator()).evaluate(game.actualState, null);
			boolean red = false;
			for(GameObject o: discarded.getAll(GameObject.class))
				if(o.getColors().contains(Color.RED))
					red = true;

			if(red)
			{
				Set chandra = parameters.get(Parameter.SOURCE);
				Set target = parameters.get(Parameter.TARGET);

				java.util.Map<Parameter, Set> damageParameters = new java.util.HashMap<Parameter, Set>();
				damageParameters.put(Parameter.SOURCE, chandra);
				damageParameters.put(Parameter.NUMBER, new Set(4));
				damageParameters.put(Parameter.TAKER, target);
				Event damage = createEvent(game, "Chandra Ablaze deals 4 damage to target creature or player", DEAL_DAMAGE_EVENLY, damageParameters);
				damage.perform(event, true);
			}

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class DiscardRedStuff extends LoyaltyAbility
	{
		public DiscardRedStuff(GameState state)
		{
			super(state, +1, "Discard a card. If a red card is discarded this way, Chandra Ablaze deals 4 damage to target creature or player.");

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			EventFactory effect = new EventFactory(CHANDRA_DISCARD, "Discard a card. If a red card is discarded this way, Chandra Ablaze deals 4 damage to target creature or player.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.CARD, InZone.instance(HandOf.instance(You.instance())));
			effect.parameters.put(EventType.Parameter.TARGET, targetedBy(target));
			this.addEffect(effect);
		}
	}

	public static final class WheelForThree extends LoyaltyAbility
	{
		public WheelForThree(GameState state)
		{
			super(state, -2, "Each player discards his or her hand, then draws three cards.");
			this.addEffect(discardHand(Players.instance(), "Each player discards his or her hand,"));
			this.addEffect(drawCards(Players.instance(), 3, "then draws three cards."));
		}
	}

	public static final class PlayRedStuff extends LoyaltyAbility
	{
		public PlayRedStuff(GameState state)
		{
			super(state, -7, "Cast any number of red instant and/or sorcery cards from your graveyard without paying their mana costs.");

			SetGenerator redInYourYard = Intersect.instance(HasColor.instance(Color.RED), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator castable = Intersect.instance(redInYourYard, HasType.instance(Type.INSTANT, Type.SORCERY));

			EventFactory castLotsOfShit = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "Cast any number of red instant and/or sorcery cards from your graveyard without paying their mana costs.");
			castLotsOfShit.parameters.put(EventType.Parameter.CAUSE, This.instance());
			castLotsOfShit.parameters.put(EventType.Parameter.PLAYER, You.instance());
			castLotsOfShit.parameters.put(EventType.Parameter.OBJECT, castable);
			this.addEffect(castLotsOfShit);
		}
	}

	public ChandraAblaze(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(5);

		// +1: Discard a card. If a red card is discarded this way, Chandra
		// Ablaze deals 4 damage to target creature or player.
		this.addAbility(new DiscardRedStuff(state));

		// -2: Each player discards his or her hand, then draws three cards.
		this.addAbility(new WheelForThree(state));

		// -7: Cast any number of red instant and/or sorcery cards from your
		// graveyard without paying their mana costs.
		this.addAbility(new PlayRedStuff(state));
	}
}
