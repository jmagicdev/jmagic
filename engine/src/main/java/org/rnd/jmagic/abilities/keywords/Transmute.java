package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Transmute extends Keyword
{
	private final String cost;

	public Transmute(GameState state, String cost)
	{
		super(state, "Transmute " + cost);
		this.cost = cost;
	}

	@Override
	public Transmute create(Game game)
	{
		return new Transmute(game.physicalState, this.cost);
	}

	public static final class TransmuteAbility extends ActivatedAbility
	{
		private final String cost;

		public TransmuteAbility(GameState state, String cost)
		{
			super(state, cost + ", Discard this card: Search your library for a card with the same converted mana cost as this card, reveal it, and put it into your hand. Then shuffle your library. Transmute only as a sorcery.");
			this.cost = cost;

			this.setManaCost(new ManaPool(cost));

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventFactory costFactory = new EventFactory(EventType.DISCARD_CARDS, "Discard this card");
			costFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			costFactory.parameters.put(EventType.Parameter.CARD, thisCard);
			this.addCost(costFactory);

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a card with the same converted mana cost as this card, reveal it, and put it into your hand. Then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasConvertedManaCost.instance(ConvertedManaCostOf.instance(thisCard))));
			this.addEffect(factory);

			this.activateOnlyFromHand();
			this.activateOnlyAtSorcerySpeed();
		}

		@Override
		public TransmuteAbility create(Game game)
		{
			return new TransmuteAbility(game.physicalState, this.cost);
		}

	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return java.util.Collections.<NonStaticAbility>singletonList(new TransmuteAbility(this.state, this.cost));
	}
}
