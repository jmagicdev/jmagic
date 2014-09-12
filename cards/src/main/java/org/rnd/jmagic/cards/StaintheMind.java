package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stain the Mind")
@Types({Type.SORCERY})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class StaintheMind extends Card
{
	public StaintheMind(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		Target t = this.addTarget(Players.instance(), "target player");
		SetGenerator graveyard = GraveyardOf.instance(targetedBy(t));
		SetGenerator hand = HandOf.instance(targetedBy(t));
		SetGenerator library = LibraryOf.instance(targetedBy(t));
		SetGenerator zones = Union.instance(graveyard, hand, library);

		// Name a nonland card.
		EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "Name a nonland card.");
		choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
		choose.parameters.put(EventType.Parameter.CHOICE, NonLandCardNames.instance());
		choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.STRING, PlayerInterface.ChooseReason.NAME_A_NONLAND_CARD));
		this.addEffect(choose);

		// Search target player's graveyard, hand, and library for any number of
		// cards with that name and exile them.
		EventFactory search = new EventFactory(EventType.SEARCH_FOR_ALL_AND_PUT_INTO, "Search target player's graveyard, hand, and library for any number of cards with that name and exile them.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.ZONE, zones);
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance(EffectResult.instance(choose))));
		search.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		this.addEffect(search);

		// Then that player shuffles his or her library.
		this.addEffect(shuffleLibrary(targetedBy(t), "Then that player shuffles his or here library."));
	}
}
