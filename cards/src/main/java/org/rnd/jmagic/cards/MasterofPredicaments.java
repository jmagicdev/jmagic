package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Master of Predicaments")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class MasterofPredicaments extends Card
{
	public static final PlayerInterface.ChooseReason CHOOSE_CARD_REASON = new PlayerInterface.ChooseReason("MasterofPredicaments", "Choose a card in your hand.", true);
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("MasterofPredicaments", "Is that card's converted mana cost greater than 4?", true);

	public static final class MasterofPredicamentsAbility1 extends EventTriggeredAbility
	{
		public MasterofPredicamentsAbility1(GameState state)
		{
			super(state, "Whenever Master of Predicaments deals combat damage to a player, choose a card in your hand. That player guesses whether the card's converted mana cost is greater than 4. If the player guessed wrong, you may cast the card without paying its mana cost.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator inHand = InZone.instance(HandOf.instance(You.instance()));
			EventFactory choose = playerChoose(You.instance(), 1, inHand, PlayerInterface.ChoiceType.OBJECTS, CHOOSE_CARD_REASON, "Choose a card in your hand.");
			this.addEffect(choose);
			SetGenerator thatCard = EffectResult.instance(choose);

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator yesOrNo = Identity.fromCollection(Answer.mayChoices());
			EventFactory guess = playerChoose(thatPlayer, 1, yesOrNo, PlayerInterface.ChoiceType.ENUM, REASON, "That player guesses whether the card's converted mana cost is greater than 4.");
			this.addEffect(guess);

			SetGenerator saidYes = Intersect.instance(EffectResult.instance(guess), Identity.instance(Answer.YES));
			SetGenerator bigCards = HasConvertedManaCost.instance(Between.instance(5, null));
			SetGenerator smallCards = RelativeComplement.instance(inHand, bigCards);
			SetGenerator guessed = IfThenElse.instance(saidYes, bigCards, smallCards);
			SetGenerator guessedWrong = RelativeComplement.instance(thatCard, guessed);

			EventFactory cast = new EventFactory(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY, "Cast the card without paying its mana cost");
			cast.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cast.parameters.put(EventType.Parameter.ALTERNATE_COST, Identity.instance(new ManaPool("")));
			cast.parameters.put(EventType.Parameter.OBJECT, thatCard);

			EventFactory maybeCast = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If the player guessed wrong, you may cast the card without paying its mana cost.");
			maybeCast.parameters.put(EventType.Parameter.IF, guessedWrong);
			maybeCast.parameters.put(EventType.Parameter.THEN, Identity.instance(youMay(cast)));
			this.addEffect(maybeCast);
		}
	}

	public MasterofPredicaments(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Master of Predicaments deals combat damage to a player,
		// choose a card in your hand. That player guesses whether the card's
		// converted mana cost is greater than 4. If the player guessed wrong,
		// you may cast the card without paying its mana cost.
		this.addAbility(new MasterofPredicamentsAbility1(state));
	}
}
