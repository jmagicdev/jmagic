package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Fauna Shaman")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class FaunaShaman extends Card
{
	public static final class FaunaShamanAbility0 extends ActivatedAbility
	{
		public FaunaShamanAbility0(GameState state)
		{
			super(state, "(G), (T), Discard a creature card: Search your library for a creature card, reveal it, and put it into your hand. Then shuffle your library.");
			this.setManaCost(new ManaPool("(G)"));
			this.costsTap = true;

			// Discard a creature card
			SetGenerator creatureCards = HasType.instance(Type.CREATURE);
			SetGenerator yourHand = HandOf.instance(You.instance());
			SetGenerator yourCreatureCards = Intersect.instance(creatureCards, InZone.instance(yourHand));

			EventFactory discard = new EventFactory(EventType.DISCARD_CHOICE, "Discard a creature card");
			discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			discard.parameters.put(EventType.Parameter.PLAYER, You.instance());
			discard.parameters.put(EventType.Parameter.CHOICE, yourCreatureCards);
			this.addCost(discard);

			EventFactory effect = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a creature card, reveal it, and put it into your hand. Then shuffle your library.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			effect.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			effect.parameters.put(EventType.Parameter.TYPE, Identity.instance(creatureCards));
			this.addEffect(effect);
		}
	}

	public FaunaShaman(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (G), (T), Discard a creature card: Search your library for a creature
		// card, reveal it, and put it into your hand. Then shuffle your
		// library.
		this.addAbility(new FaunaShamanAbility0(state));
	}
}
