package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Increasing Ambition")
@Types({Type.SORCERY})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class IncreasingAmbition extends Card
{
	public IncreasingAmbition(GameState state)
	{
		super(state);

		// Search your library for a card and put that card into your hand. If
		// Increasing Ambition was cast from a graveyard, instead search your
		// library for two cards and put those cards into your hand. Then
		// shuffle your library.
		SetGenerator number = IfThenElse.instance(Intersect.instance(ZoneCastFrom.instance(This.instance()), GraveyardOf.instance(Players.instance())), numberGenerator(2), numberGenerator(1));

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.NUMBER, number);
		parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, parameters, "Search your library for a card and put that card into your hand. If Increasing Ambition was cast from a graveyard, instead search your library for two cards and put those cards into your hand. Then shuffle your library."));

		// Flashback (7)(B) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(7)(B)"));
	}
}
