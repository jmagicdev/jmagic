package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Perish the Thought")
@Types({Type.SORCERY})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class PerishtheThought extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("PerishtheThought", "Choose a card from that player's hand.", true);

	public PerishtheThought(GameState state)
	{
		super(state);

		// Target opponent reveals his or her hand.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		SetGenerator cardsInHand = InZone.instance(HandOf.instance(target));

		EventFactory reveal = new EventFactory(EventType.REVEAL, "Target opponent reveals his or her hand.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, cardsInHand);
		this.addEffect(reveal);

		EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "You choose a card from it.");
		choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
		choose.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		choose.parameters.put(EventType.Parameter.CHOICE, cardsInHand);
		choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, REASON));
		this.addEffect(choose);

		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "That player shuffles that card into his or her library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(EffectResult.instance(choose), target));
		this.addEffect(shuffle);
	}
}
