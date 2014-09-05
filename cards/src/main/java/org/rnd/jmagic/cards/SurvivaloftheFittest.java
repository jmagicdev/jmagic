package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Survival of the Fittest")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Exodus.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class SurvivaloftheFittest extends Card
{
	public static final class SurvivaloftheFittestAbility0 extends ActivatedAbility
	{
		public SurvivaloftheFittestAbility0(GameState state)
		{
			super(state, "(G), Discard a creature card: Search your library for a creature card, reveal that card, and put it into your hand. Then shuffle your library.");
			this.setManaCost(new ManaPool("(G)"));
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

	public SurvivaloftheFittest(GameState state)
	{
		super(state);

		// (G), Discard a creature card: Search your library for a creature
		// card, reveal that card, and put it into your hand. Then shuffle your
		// library.
		this.addAbility(new SurvivaloftheFittestAbility0(state));
	}
}
