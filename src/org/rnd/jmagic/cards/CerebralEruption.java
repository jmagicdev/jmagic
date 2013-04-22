package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cerebral Eruption")
@Types({Type.SORCERY})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class CerebralEruption extends Card
{
	public CerebralEruption(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

		// Target opponent reveals the top card of his or her library.
		EventFactory reveal = new EventFactory(EventType.REVEAL, "Target opponent reveals the top card of his or her library.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(1, LibraryOf.instance(target)));
		this.addEffect(reveal);

		SetGenerator revealedCard = EffectResult.instance(reveal);

		// Cerebral Eruption deals damage equal to the revealed card's converted
		// mana cost to that player and each creature he or she controls.
		this.addEffect(spellDealDamage(ConvertedManaCostOf.instance(revealedCard), Union.instance(target, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(target))), "Cerebral Eruption deals damage equal to the revealed card's converted mana cost to that player and each creature he or she controls."));

		// If a land card is revealed this way, return Cerebral Eruption to its
		// owner's hand.
		EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return Cerebral Eruption to its owner's hand.");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.TO, HandOf.instance(OwnerOf.instance(This.instance())));
		move.parameters.put(EventType.Parameter.OBJECT, This.instance());

		EventFactory factory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If a land card is revealed this way, return Cerebral Eruption to its owner's hand.");
		factory.parameters.put(EventType.Parameter.IF, Intersect.instance(revealedCard, HasType.instance(Type.LAND)));
		factory.parameters.put(EventType.Parameter.THEN, Identity.instance(move));
		this.addEffect(factory);
	}
}
