package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chord of Calling")
@Types({Type.INSTANT})
@ManaCost("XGGG")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ChordofCalling extends Card
{
	public ChordofCalling(GameState state)
	{
		super(state);

		// Convoke (Each creature you tap while casting this spell reduces its
		// cost by (1) or by one mana of that creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Search your library for a creature card with converted mana cost X or
		// less and put it onto the battlefield. Then shuffle your library.
		SetGenerator creatureCards = HasType.instance(Type.CREATURE);
		SetGenerator cmcXOrLess = HasConvertedManaCost.instance(Between.instance(Empty.instance(), ValueOfX.instance(This.instance())));
		SetGenerator searchFor = Intersect.instance(creatureCards, cmcXOrLess);

		EventFactory effect = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a creature card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
		effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		effect.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		effect.parameters.put(EventType.Parameter.TYPE, Identity.instance(searchFor));
		this.addEffect(effect);
	}
}
