package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Warp World")
@Types({Type.SORCERY})
@ManaCost("5RRR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class WarpWorld extends Card
{
	public WarpWorld(GameState state)
	{
		super(state);
		SetGenerator eachPlayer = DynamicEvaluation.instance();

		// Each player shuffles all permanents he or she owns into his or her
		// library,
		EventFactory onePlayerShuffles = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Each player shuffles all permanents he or she owns into his or her library,");
		onePlayerShuffles.parameters.put(EventType.Parameter.CAUSE, This.instance());
		onePlayerShuffles.parameters.put(EventType.Parameter.OBJECT, Union.instance(Intersect.instance(Permanents.instance(), OwnedBy.instance(eachPlayer)), eachPlayer));

		EventFactory eachPlayerShuffles = new EventFactory(FOR_EACH_PLAYER, "Each player shuffles all permanents he or she owns into his or her library,");
		eachPlayerShuffles.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		eachPlayerShuffles.parameters.put(EventType.Parameter.EFFECT, Identity.instance(onePlayerShuffles));
		this.addEffect(eachPlayerShuffles);

		// then reveals that many cards from the top of his or her library.
		SetGenerator hisLibrary = LibraryOf.instance(eachPlayer);
		SetGenerator thatMany = Count.instance(RelativeComplement.instance(ForEachResult.instance(eachPlayerShuffles, eachPlayer), hisLibrary));
		SetGenerator thatManyCards = TopCards.instance(thatMany, hisLibrary);

		EventFactory oneReveal = new EventFactory(EventType.REVEAL, "then reveals that many cards from the top of his or her library.");
		oneReveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		oneReveal.parameters.put(EventType.Parameter.OBJECT, thatManyCards);

		EventFactory eachReveal = new EventFactory(FOR_EACH_PLAYER, "then reveals that many cards from the top of his or her library.");
		eachReveal.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		eachReveal.parameters.put(EventType.Parameter.EFFECT, Identity.instance(oneReveal));
		this.addEffect(eachReveal);

		// The events from this point forward must move cards around
		// simultaneously for all players rather than using separate events for
		// each player, so we aggregate the results of the reveal to facilitate
		// this.
		SetGenerator revealedCards = ForEachResult.instance(eachReveal, Players.instance());

		// Each player puts all artifact, creature, and land cards revealed this
		// way onto the battlefield,
		SetGenerator revealedNonEnchantments = Intersect.instance(HasType.instance(Type.ARTIFACT, Type.CREATURE, Type.LAND), revealedCards);

		EventFactory putNonEnchantments = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Each player puts all artifact, creature, and land cards revealed this way onto the battlefield,");
		putNonEnchantments.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putNonEnchantments.parameters.put(EventType.Parameter.OBJECT, revealedNonEnchantments);
		this.addEffect(putNonEnchantments);

		// then does the same for enchantment cards,
		SetGenerator revealedEnchantments = Intersect.instance(HasType.instance(Type.ENCHANTMENT), revealedCards);

		EventFactory putEnchantments = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "then does the same for enchantment cards,");
		putEnchantments.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putEnchantments.parameters.put(EventType.Parameter.OBJECT, revealedEnchantments);
		this.addEffect(putEnchantments);

		// then puts all cards revealed this way that weren't put onto the
		// battlefield on the bottom of his or her library.
		SetGenerator otherRevealedCards = RelativeComplement.instance(revealedCards, Union.instance(revealedNonEnchantments, revealedEnchantments));

		EventFactory restToBottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "then puts all cards revealed this way that weren't put onto the battlefield on the bottom of his or her library.");
		restToBottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
		restToBottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
		restToBottom.parameters.put(EventType.Parameter.OBJECT, otherRevealedCards);
		this.addEffect(restToBottom);
	}
}
