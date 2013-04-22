package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Horizon Spellbomb")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class HorizonSpellbomb extends Card
{
	public static final class HorizonSpellbombAbility0 extends ActivatedAbility
	{
		public HorizonSpellbombAbility0(GameState state)
		{
			super(state, "(2), (T), Sacrifice Horizon Spellbomb: Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Horizon Spellbomb"));

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));

			this.addEffect(youMay(search, "You may search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library."));
		}
	}

	public HorizonSpellbomb(GameState state)
	{
		super(state);

		// (2), (T), Sacrifice Horizon Spellbomb: Search your library for a
		// basic land card, reveal it, and put it into your hand. Then shuffle
		// your library.
		this.addAbility(new HorizonSpellbombAbility0(state));

		// When Horizon Spellbomb is put into a graveyard from the battlefield,
		// you may pay (G). If you do, draw a card.
		this.addAbility(new org.rnd.jmagic.abilities.ScarsSpellbomb(state, this.getName(), "(G)"));
	}
}
