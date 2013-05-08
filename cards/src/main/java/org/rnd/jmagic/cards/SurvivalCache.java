package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Survival Cache")
@Types({Type.SORCERY})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class SurvivalCache extends Card
{
	public SurvivalCache(GameState state)
	{
		super(state);

		// You gain 2 life.
		this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));

		// Then if you have more life than an opponent, draw a card.
		EventFactory draw = drawCards(You.instance(), 1, "Draw a card");

		SetGenerator lowestOpponent = Minimum.instance(LifeTotalOf.instance(OpponentsOf.instance(You.instance())));
		SetGenerator youHaveMore = Intersect.instance(LifeTotalOf.instance(You.instance()), Between.instance(lowestOpponent, Empty.instance()));

		EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Then if you have more life than an opponent, draw a card.");
		effect.parameters.put(EventType.Parameter.IF, youHaveMore);
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(draw));
		this.addEffect(effect);

		// Rebound (If you cast this spell from your hand, exile it as it
		// resolves. At the beginning of your next upkeep, you may cast this
		// card from exile without paying its mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Rebound(state));
	}
}
