package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Desperate Ravings")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class DesperateRavings extends Card
{
	public DesperateRavings(GameState state)
	{
		super(state);

		// Draw two cards, then discard a card at random.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards,"));

		EventFactory discard = new EventFactory(EventType.DISCARD_RANDOM, "then discard a card at random.");
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.PLAYER, You.instance());
		discard.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		this.addEffect(discard);

		// Flashback (2)(U) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(2)(U)"));
	}
}
