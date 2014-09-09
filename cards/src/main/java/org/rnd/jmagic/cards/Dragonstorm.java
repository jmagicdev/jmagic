package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dragonstorm")
@Types({Type.SORCERY})
@ManaCost("8R")
@ColorIdentity({Color.RED})
public final class Dragonstorm extends Card
{
	public Dragonstorm(GameState state)
	{
		super(state);

		// Search your library for a Dragon permanent card and put it onto the
		// battlefield. Then shuffle your library.
		SetGenerator dragons = HasSubType.instance(SubType.DRAGON);
		SetGenerator permanents = HasType.instance(Type.permanentTypes());
		SetGenerator dragonPermanentCards = Intersect.instance(dragons, permanents, Cards.instance());

		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Dragon permanent card and put it onto the battlefield.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(dragonPermanentCards));
		this.addEffect(search);

		this.addEffect(shuffleYourLibrary("Then shuffle your library."));

		// Storm (When you cast this spell, copy it for each spell cast before
		// it this turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Storm(state));
	}
}
