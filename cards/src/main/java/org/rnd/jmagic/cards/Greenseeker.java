package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Greenseeker")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SPELLSHAPER})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class Greenseeker extends Card
{
	public static final class Greenseek extends ActivatedAbility
	{
		public Greenseek(GameState state)
		{
			super(state, "(G), (T), Discard a card: Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.");
			this.setManaCost(new ManaPool("G"));
			this.costsTap = true;

			EventFactory discard = new EventFactory(EventType.DISCARD_CHOICE, "Discard a card");
			discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			discard.parameters.put(EventType.Parameter.PLAYER, You.instance());
			discard.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addCost(discard);

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(search);
		}
	}

	public Greenseeker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (G), (T), Discard a card: Search your library for a basic land card,
		// reveal it, and put it into your hand. Then shuffle your library.
		this.addAbility(new Greenseek(state));
	}
}
