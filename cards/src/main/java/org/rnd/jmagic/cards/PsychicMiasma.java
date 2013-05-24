package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Psychic Miasma")
@Types({Type.SORCERY})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class PsychicMiasma extends Card
{
	public PsychicMiasma(GameState state)
	{
		super(state);

		// Target player discards a card. If a land card is discarded this way,
		// return Psychic Miasma to its owner's hand.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		EventFactory discard = discardCards(target, 1, "Target player discards a card.");
		this.addEffect(discard);

		EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return Psychic Miasma to its owner's hand.");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.TO, HandOf.instance(OwnerOf.instance(This.instance())));
		move.parameters.put(EventType.Parameter.OBJECT, This.instance());

		EventFactory ifFactory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If a land card is discarded this way, return Psychic Miasma to its owner's hand.");
		ifFactory.parameters.put(EventType.Parameter.IF, Intersect.instance(HasType.instance(Type.LAND), NewObjectOf.instance(EffectResult.instance(discard))));
		ifFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(move));
		this.addEffect(ifFactory);
	}
}
