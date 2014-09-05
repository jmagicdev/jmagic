package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deicide")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.JOURNEY_INTO_NYX, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Deicide extends Card
{
	public Deicide(GameState state)
	{
		super(state);


		// Exile target enchantment.
		SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "target enchantment"));
		EventFactory exile = exile(target, "Exile target enchantment.");
		this.addEffect(exile);

		// If the exiled card is a God card, search its controller's graveyard,
		// hand, and library for any number of cards with the same name as that
		// card and exile them, then that player shuffles his or her library.
		SetGenerator exiledCard = NewObjectOf.instance(EffectResult.instance(exile));
		SetGenerator isAGodCard = Intersect.instance(HasSubType.instance(SubType.GOD), exiledCard);

		SetGenerator itsController = ControllerOf.instance(target);

		SetGenerator graveyard = GraveyardOf.instance(itsController);
		SetGenerator hand = HandOf.instance(itsController);
		SetGenerator library = LibraryOf.instance(itsController);
		SetGenerator zones = Union.instance(graveyard, hand, library);

		EventFactory search = new EventFactory(EventType.SEARCH_FOR_ALL_AND_PUT_INTO, "Search its controller's graveyard, hand, and library for any number of cards with the same name as that card and exile them, then that player shuffles his or her library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.ZONE, zones);
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance(NameOf.instance(exiledCard))));
		search.parameters.put(EventType.Parameter.TO, ExileZone.instance());

		EventFactory killGods = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If the exiled card is a God card, search its controller's graveyard, hand, and library for any number of cards with the same name as that card and exile them, then that player shuffles his or her library.");
		killGods.parameters.put(EventType.Parameter.IF, isAGodCard);
		killGods.parameters.put(EventType.Parameter.THEN, Identity.instance(search));
		this.addEffect(killGods);
	}
}
