package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tooth and Nail")
@Types({Type.SORCERY})
@ManaCost("5GG")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ToothandNail extends Card
{
	public ToothandNail(GameState state)
	{
		super(state);

		// Choose one \u2014 Search your library for up to two creature cards,
		// reveal them, put them into your hand, then shuffle your library; or
		// put up to two creature cards from your hand onto the battlefield.

		SetGenerator creatures = HasType.instance(Type.CREATURE);
		SetGenerator yourHand = HandOf.instance(You.instance());

		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to two creature cards, reveal them, put them into your hand, then shuffle your library");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 2));
		search.parameters.put(EventType.Parameter.TO, yourHand);
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(creatures));
		this.addEffect(1, search);

		EventFactory put = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "put up to two creature cards from your hand onto the battlefield.");
		put.parameters.put(EventType.Parameter.CAUSE, This.instance());
		put.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		put.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(creatures, InZone.instance(yourHand)));
		put.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 2));
		this.addEffect(2, put);

		// Entwine (2) (Choose both if you pay the entwine cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Entwine(state, "(2)"));
	}
}
