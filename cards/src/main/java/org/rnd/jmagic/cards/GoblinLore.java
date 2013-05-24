package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Lore")
@Types({Type.SORCERY})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.STARTER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.PORTAL_SECOND_AGE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class GoblinLore extends Card
{
	public GoblinLore(GameState state)
	{
		super(state);

		this.addEffect(drawCards(You.instance(), 4, "Draw four cards,"));

		EventType.ParameterMap discardParameters = new EventType.ParameterMap();
		discardParameters.put(EventType.Parameter.CAUSE, This.instance());
		discardParameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
		discardParameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(new EventFactory(EventType.DISCARD_RANDOM, discardParameters, "then discard three cards at random."));
	}
}
