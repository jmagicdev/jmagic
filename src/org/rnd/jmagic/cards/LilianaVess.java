package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Liliana Vess")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.LILIANA})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class LilianaVess extends Card
{
	public static final class Discard extends LoyaltyAbility
	{
		public Discard(GameState state)
		{
			// +1:
			super(state, +1, "Target player discards a card.");

			// Target player
			Target target = this.addTarget(Players.instance(), "target player");

			// discards a card.
			this.addEffect(discardCards(targetedBy(target), 1, "Target player discards a card."));
		}
	}

	public static final class Tutor extends LoyaltyAbility
	{
		public Tutor(GameState state)
		{
			// -2:
			super(state, -2, "Search your library for a card, then shuffle your library and put that card on top of it.");

			// Search your library for a card, then shuffle your library and put
			// that card on top of it.
			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_ON_TOP, parameters, "Search your library for a card, then shuffle your library and put that card on top of it."));
		}
	}

	public static final class BigZombify extends LoyaltyAbility
	{
		public BigZombify(GameState state)
		{
			// -8:
			super(state, -8, "Put all creature cards from all graveyards onto the battlefield under your control.");

			// Put all creature cards in all graveyards
			SetGenerator creatureCards = HasType.instance(Type.CREATURE);
			SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
			SetGenerator creaturesInYards = Intersect.instance(creatureCards, inGraveyards);

			// onto the battlefield under your control.
			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			parameters.put(EventType.Parameter.OBJECT, creaturesInYards);
			this.addEffect(new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, parameters, "Put all creature cards from all graveyards onto the battlefield under your control."));
		}
	}

	public LilianaVess(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(5);

		this.addAbility(new Discard(state));
		this.addAbility(new Tutor(state));
		this.addAbility(new BigZombify(state));
	}
}
