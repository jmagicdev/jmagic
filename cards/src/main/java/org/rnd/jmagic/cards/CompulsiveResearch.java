package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Compulsive Research")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CompulsiveResearch extends Card
{
	public CompulsiveResearch(GameState state)
	{
		super(state);

		// Target player
		Target target = this.addTarget(Players.instance(), "target player");

		// draws three cards.
		this.addEffect(drawCards(targetedBy(target), 3, "Target player draws three cards."));

		// Then that player discards two cards unless he or she discards a land
		// card.
		EventFactory discardLand = new EventFactory(EventType.DISCARD_CHOICE, "That player discards a land card.");
		discardLand.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discardLand.parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
		discardLand.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		discardLand.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(InZone.instance(HandOf.instance(targetedBy(target))), HasType.instance(Type.LAND)));

		EventFactory mayDiscardLand = new EventFactory(EventType.PLAYER_MAY, "That player may discard a land card.");
		mayDiscardLand.parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
		mayDiscardLand.parameters.put(EventType.Parameter.EVENT, Identity.instance(discardLand));

		EventFactory discardTwo = discardCards(targetedBy(target), 2, "That player discards two cards");

		EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Then that player discards two cards unless he or she discards a land card.");
		effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayDiscardLand));
		effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(discardTwo));
		this.addEffect(effect);
	}
}
