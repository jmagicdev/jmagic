package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Molten Psyche")
@Types({Type.SORCERY})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class MoltenPsyche extends Card
{
	public MoltenPsyche(GameState state)
	{
		super(state);

		SetGenerator eachPlayer = DynamicEvaluation.instance();

		// Each player shuffles the cards from his or her hand into his or her
		// library,
		EventFactory onePlayerShuffles = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Each player shuffles the cards from his or her hand into his or her library,");
		onePlayerShuffles.parameters.put(EventType.Parameter.CAUSE, This.instance());
		onePlayerShuffles.parameters.put(EventType.Parameter.OBJECT, Union.instance(InZone.instance(HandOf.instance(eachPlayer)), eachPlayer));

		EventFactory eachPlayerShuffles = new EventFactory(FOR_EACH_PLAYER, "Each player shuffles the cards from his or her hand into his or her library,");
		eachPlayerShuffles.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		eachPlayerShuffles.parameters.put(EventType.Parameter.EFFECT, Identity.instance(onePlayerShuffles));
		this.addEffect(eachPlayerShuffles);

		// then draws that many cards.
		SetGenerator thatMany = Count.instance(RelativeComplement.instance(ForEachResult.instance(eachPlayerShuffles, eachPlayer), LibraryOf.instance(eachPlayer)));

		EventFactory oneDraw = drawCards(eachPlayer, thatMany, "then draws that many cards.");
		EventFactory eachDraw = new EventFactory(FOR_EACH_PLAYER, "then draws that many cards.");
		eachDraw.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		eachDraw.parameters.put(EventType.Parameter.EFFECT, Identity.instance(oneDraw));
		this.addEffect(eachDraw);

		// Metalcraft \u2014 If you control three or more artifacts, Molten
		// Psyche deals damage to each opponent equal to the number of cards
		// that player has drawn this turn.

		state.ensureTracker(new NumberCardsDrawnBy.DrawCounter());

		EventFactory damageOne = spellDealDamage(NumberCardsDrawnBy.instance(eachPlayer), eachPlayer, "Molten Psyche deals damage to each opponent equal to the number of cards that player has drawn this turn.");
		EventFactory damageEach = new EventFactory(FOR_EACH_PLAYER, "Molten Psyche deals damage to each opponent equal to the number of cards that player has drawn this turn.");
		damageEach.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		damageEach.parameters.put(EventType.Parameter.EFFECT, Identity.instance(damageOne));
		this.addEffect(damageEach);
	}
}
