package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Citanul Flute")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({})
public final class CitanulFlute extends Card
{
	public static final class Fetch extends ActivatedAbility
	{
		public Fetch(GameState state)
		{
			super(state, "(X), (T): Search your library for a creature card with converted mana cost X or less, reveal it, and put it into your hand. Then shuffle your library.");

			this.setManaCost(new ManaPool("X"));

			this.costsTap = true;

			SetGenerator filter = Intersect.instance(HasType.instance(Type.CREATURE), HasConvertedManaCost.instance(Between.instance(Empty.instance(), ValueOfX.instance(This.instance()))));
			EventType.ParameterMap searchParameters = new EventType.ParameterMap();
			searchParameters.put(EventType.Parameter.CAUSE, This.instance());
			searchParameters.put(EventType.Parameter.PLAYER, You.instance());
			searchParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			searchParameters.put(EventType.Parameter.CARD, LibraryOf.instance(You.instance()));
			searchParameters.put(EventType.Parameter.TYPE, Identity.instance(filter));
			searchParameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, searchParameters, "Search your library for a creature card with converted mana cost X or less, reveal it, and put it into your hand. If you do, shuffle your library."));
		}
	}

	public CitanulFlute(GameState state)
	{
		super(state);

		this.addAbility(new Fetch(state));
	}
}
