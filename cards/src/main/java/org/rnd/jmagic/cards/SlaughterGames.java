package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Slaughter Games")
@Types({Type.SORCERY})
@ManaCost("2BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class SlaughterGames extends Card
{
	public SlaughterGames(GameState state)
	{
		super(state);

		// Slaughter Games can't be countered by spells or abilities.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, "Slaughter Games", true));

		// Name a nonland card. Search target opponent's graveyard, hand, and
		// library for any number of cards with that name and exile them. Then
		// that player shuffles his or her library.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		SetGenerator graveyard = GraveyardOf.instance(target);
		SetGenerator hand = HandOf.instance(target);
		SetGenerator library = LibraryOf.instance(target);
		SetGenerator zones = Union.instance(graveyard, hand, library);

		EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "Name a nonland card.");
		choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
		choose.parameters.put(EventType.Parameter.CHOICE, NonLandCardNames.instance());
		choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.STRING, PlayerInterface.ChooseReason.NAME_A_NONLAND_CARD));
		this.addEffect(choose);

		EventFactory search = new EventFactory(EventType.SEARCH_FOR_ALL_AND_PUT_INTO, "Search target opponent's graveyard, hand, and library for any number of cards with that name and exile them.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.ZONE, zones);
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance(EffectResult.instance(choose))));
		search.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		this.addEffect(search);

		this.addEffect(shuffleLibrary(target, "Then that player shuffles his or her library."));
	}
}
