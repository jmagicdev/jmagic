package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Guul Draz Specter")
@Types({Type.CREATURE})
@SubTypes({SubType.SPECTER})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class GuulDrazSpecter extends Card
{
	public static final class NoCardsPump extends StaticAbility
	{
		public NoCardsPump(GameState state)
		{
			super(state, "Guul Draz Specter gets +3/+3 as long as an opponent has no cards in hand.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), (+3), (+3)));

			SetGenerator cardsInOpponentsHands = InZone.instance(HandOf.instance(OpponentsOf.instance(You.instance())));
			SetGenerator noCards = Intersect.instance(numberGenerator(0), cardsInOpponentsHands);
			this.canApply = Both.instance(this.canApply, noCards);
		}
	}

	public static final class CombatDamageDiscard extends EventTriggeredAbility
	{
		public CombatDamageDiscard(GameState state)
		{
			super(state, "Whenever Guul Draz Specter deals combat damage to a player, that player discards a card.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator opponents = OpponentsOf.instance(ControllerOf.instance(thisCard));
			SetGenerator trigger = TriggerDamage.instance(This.instance());
			SetGenerator takers = Intersect.instance(opponents, TakerOfDamage.instance(DamageDealtBy.instance(thisCard, trigger)));

			EventType.ParameterMap discardParameters = new EventType.ParameterMap();
			discardParameters.put(EventType.Parameter.CAUSE, This.instance());
			discardParameters.put(EventType.Parameter.PLAYER, takers);
			discardParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addEffect(new EventFactory(EventType.DISCARD_CHOICE, discardParameters, "That player discards a card"));
		}
	}

	public GuulDrazSpecter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Guul Draz Specter gets +3/+3 as long as an opponent has no cards in
		// hand.
		this.addAbility(new NoCardsPump(state));

		// Whenever Guul Draz Specter deals combat damage to a player, that
		// player discards a card.
		this.addAbility(new CombatDamageDiscard(state));
	}
}
