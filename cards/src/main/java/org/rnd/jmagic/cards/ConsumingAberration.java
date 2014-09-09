package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Consuming Aberration")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("3UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class ConsumingAberration extends Card
{
	public static final class ConsumingAberrationAbility0 extends CharacteristicDefiningAbility
	{
		public ConsumingAberrationAbility0(GameState state)
		{
			super(state, "Consuming Aberration's power and toughness are each equal to the number of cards in your opponents' graveyards.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator number = Count.instance(InZone.instance(GraveyardOf.instance(OpponentsOf.instance(You.instance()))));
			this.addEffectPart(setPowerAndToughness(This.instance(), number, number));
		}
	}

	public static final class ConsumingAberrationAbility1 extends EventTriggeredAbility
	{
		public ConsumingAberrationAbility1(GameState state)
		{
			super(state, "Whenever you cast a spell, each opponent reveals cards from the top of his or her library until he or she reveals a land card, then puts those cards into his or her graveyard.");

			this.addPattern(whenYouCastASpell());

			DynamicEvaluation eachOpponent = DynamicEvaluation.instance();

			SetGenerator toReveal = TopMost.instance(LibraryOf.instance(eachOpponent), numberGenerator(1), HasType.instance(Type.LAND));
			EventFactory reveal = reveal(toReveal, "Each opponent reveals cards from the top of his or her library until he or she reveals a land card,");
			EventFactory move = putIntoGraveyard(toReveal, "then puts those cards into his or her graveyard.");

			EventFactory forEach = new EventFactory(FOR_EACH_PLAYER, "Each opponent reveals cards from the top of his or her library until he or she reveals a land card, then puts those cards into his or her graveyard.");
			forEach.parameters.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			forEach.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachOpponent));
			forEach.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sequence(reveal, move)));
			this.addEffect(forEach);
		}
	}

	public ConsumingAberration(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Consuming Aberration's power and toughness are each equal to the
		// number of cards in your opponents' graveyards.
		this.addAbility(new ConsumingAberrationAbility0(state));

		// Whenever you cast a spell, each opponent reveals cards from the top
		// of his or her library until he or she reveals a land card, then puts
		// those cards into his or her graveyard.
		this.addAbility(new ConsumingAberrationAbility1(state));
	}
}
