package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phantasmagorian")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("5BB")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Phantasmagorian extends Card
{
	public static final class PhantasmagorianAbility0 extends EventTriggeredAbility
	{
		public PhantasmagorianAbility0(GameState state)
		{
			super(state, "When you cast Phantasmagorian, any player may discard three cards. If a player does, counter Phantasmagorian.");

			this.addPattern(whenYouCastThisSpell());

			DynamicEvaluation player = DynamicEvaluation.instance();

			EventFactory discard = discardCards(player, 3, "Discard three cards.");
			EventFactory counter = counter(ABILITY_SOURCE_OF_THIS, "Counter Phantasmagorian.");
			EventFactory individualEffect = ifThen(playerMay(player, discard, "Any player may discard three cards."), counter, "Any player may discard three cards. If a player does, counter Phantasmagorian.");

			EventFactory factory = new EventFactory(FOR_EACH_PLAYER, "Any player may discard three cards. If a player does, counter Phantasmagorian.");
			factory.parameters.put(EventType.Parameter.TARGET, Identity.instance(player));
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(individualEffect));
			this.addEffect(factory);
		}
	}

	public static final class PhantasmagorianAbility1 extends ActivatedAbility
	{
		public PhantasmagorianAbility1(GameState state)
		{
			super(state, "Discard three cards: Return Phantasmagorian from your graveyard to your hand.");
			// Discard three cards
			this.addCost(discardCards(You.instance(), 3, "Discard three cards"));

			this.addEffect(putIntoHand(ABILITY_SOURCE_OF_THIS, You.instance(), "Return Phantasmagorian from your graveyard to your hand."));

			this.activateOnlyFromGraveyard();
		}
	}

	public Phantasmagorian(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// When you cast Phantasmagorian, any player may discard three cards. If
		// a player does, counter Phantasmagorian.
		this.addAbility(new PhantasmagorianAbility0(state));

		// Discard three cards: Return Phantasmagorian from your graveyard to
		// your hand.
		this.addAbility(new PhantasmagorianAbility1(state));
	}
}
