package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Duskmantle Seer")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.WIZARD})
@ManaCost("2UB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DuskmantleSeer extends Card
{
	public static final class DuskmantleSeerAbility1 extends EventTriggeredAbility
	{
		public DuskmantleSeerAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, each player reveals the top card of his or her library, loses life equal to that card's converted mana cost, then puts it into his or her hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			DynamicEvaluation eachPlayer = DynamicEvaluation.instance();

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(eachPlayer));
			EventFactory reveal = reveal(topCard, "Each player reveals the top card of his or her library,");
			EventFactory loseLife = loseLife(eachPlayer, ConvertedManaCostOf.instance(topCard), "loses life equal to that card's converted mana cost,");
			EventFactory intoHand = putIntoHand(topCard, eachPlayer, "then puts it into his or her hand.");

			EventFactory forEachPlayer = new EventFactory(FOR_EACH_PLAYER, "Each player reveals the top card of his or her library, loses life equal to that card's converted mana cost, then puts it into his or her hand.");
			forEachPlayer.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
			forEachPlayer.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sequence(reveal, loseLife, intoHand)));
			this.addEffect(forEachPlayer);
		}
	}

	public DuskmantleSeer(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, each player reveals the top card of
		// his or her library, loses life equal to that card's converted mana
		// cost, then puts it into his or her hand.
		this.addAbility(new DuskmantleSeerAbility1(state));
	}
}
