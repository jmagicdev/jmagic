package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Descendants' Path")
@Types({Type.ENCHANTMENT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class DescendantsPath extends Card
{
	public static final class DescendantsPathAbility0 extends EventTriggeredAbility
	{
		public DescendantsPathAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, reveal the top card of your library. If it's a creature card that shares a creature type with a creature you control, you may cast that card without paying its mana cost. Otherwise, put that card on the bottom of your library.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			// reveal the top card of your library.
			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			EventType.ParameterMap revealParameters = new EventType.ParameterMap();
			revealParameters.put(EventType.Parameter.CAUSE, This.instance());
			revealParameters.put(EventType.Parameter.OBJECT, topCard);
			this.addEffect(new EventFactory(EventType.REVEAL, revealParameters, "Reveal the top card of your library."));

			// If it's a creature card, you may cast that card without paying
			// its mana cost.
			EventType.ParameterMap castParameters = new EventType.ParameterMap();
			castParameters.put(EventType.Parameter.PLAYER, You.instance());
			castParameters.put(EventType.Parameter.OBJECT, topCard);
			castParameters.put(EventType.Parameter.ALTERNATE_COST, Empty.instance());

			// Otherwise, put that card on the bottom of your library.
			EventType.ParameterMap bottomParameters = new EventType.ParameterMap();
			bottomParameters.put(EventType.Parameter.CAUSE, This.instance());
			bottomParameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			bottomParameters.put(EventType.Parameter.OBJECT, topCard);

			SetGenerator itsACreature = Intersect.instance(topCard, HasType.instance(Type.CREATURE));
			SetGenerator thatSharesACreatureTypeWithACreatureYouControl = Intersect.instance(SubTypesOf.instance(topCard, Type.CREATURE), SubTypesOf.instance(CREATURES_YOU_CONTROL, Type.CREATURE));

			EventType.ParameterMap ifParameters = new EventType.ParameterMap();
			ifParameters.put(EventType.Parameter.IF, Both.instance(itsACreature, thatSharesACreatureTypeWithACreatureYouControl));
			ifParameters.put(EventType.Parameter.THEN, Identity.instance(new EventFactory(EventType.PLAYER_MAY_CAST, castParameters, "You may cast that card without paying its mana cost.")));
			ifParameters.put(EventType.Parameter.ELSE, Identity.instance(new EventFactory(EventType.PUT_INTO_LIBRARY, bottomParameters, "Put that card on the bottom of your library.")));
			this.addEffect(new EventFactory(EventType.IF_CONDITION_THEN_ELSE, ifParameters, "If it's a creature card that shares a creature type with a creature you control, you may cast that card without paying its mana cost. Otherwise, put that card on the bottom of your library."));
		}
	}

	public DescendantsPath(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, reveal the top card of your library.
		// If it's a creature card that shares a creature type with a creature
		// you control, you may cast that card without paying its mana cost.
		// Otherwise, put that card on the bottom of your library.
		this.addAbility(new DescendantsPathAbility0(state));
	}
}
