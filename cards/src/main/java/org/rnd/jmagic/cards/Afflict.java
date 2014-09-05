package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Afflict")
@Types({Type.INSTANT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Afflict extends Card
{
	public Afflict(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (-1), (-1), "Target creature gets -1/-1 until end of turn."));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
