package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Recurring Insight")
@Types({Type.SORCERY})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class RecurringInsight extends Card
{
	public RecurringInsight(GameState state)
	{
		super(state);

		// Draw cards equal to the number of cards in target opponent's hand.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		this.addEffect(drawCards(You.instance(), Count.instance(InZone.instance(HandOf.instance(target))), "Draw cards equal to the number of cards in target opponent's hand."));

		// Rebound
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Rebound(state));
	}
}
