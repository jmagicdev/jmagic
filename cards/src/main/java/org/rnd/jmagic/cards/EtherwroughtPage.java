package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Etherwrought Page")
@Types({Type.ARTIFACT})
@ManaCost("1WUB")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class EtherwroughtPage extends Card
{
	public static final class DullSwissArmyKnife extends EventTriggeredAbility
	{
		public DullSwissArmyKnife(GameState state)
		{
			super(state, "At the beginning of your upkeep, choose one \u2014\n\u2022 You gain 2 life.\n\u2022 Look at the top card of your library, then you may put that card into your graveyard.\n\u2022 Each opponent loses 1 life.");

			// At the beginning of your upkeep,
			this.addPattern(atTheBeginningOfYourUpkeep());

			// choose one -
			// You gain 2 life;
			this.addEffect(1, gainLife(You.instance(), 2, "You gain 2 life"));

			// or look at the top card of your library, then you may put that
			// card into your graveyard;
			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			EventFactory look = new EventFactory(EventType.LOOK, "look at the top card of your library,");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.OBJECT, topCard);
			this.addEffect(2, look);

			EventFactory dump = putIntoGraveyard(EffectResult.instance(look), "Put that card into your graveyard");
			this.addEffect(2, youMay(dump, "then you may put that card into your graveyard"));

			// or each opponent loses 1 life.
			this.addEffect(3, loseLife(OpponentsOf.instance(You.instance()), 1, "each opponent loses 1 life."));
		}
	}

	public EtherwroughtPage(GameState state)
	{
		super(state);

		this.addAbility(new DullSwissArmyKnife(state));
	}
}
