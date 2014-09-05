package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Aggressive Urge")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class AggressiveUrge extends Card
{
	public AggressiveUrge(GameState state)
	{
		super(state);
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+1), (+1), "Target creature gets +1/+1 until end of turn."));
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
