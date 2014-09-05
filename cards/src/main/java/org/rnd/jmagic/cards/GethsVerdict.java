package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Geth's Verdict")
@Types({Type.INSTANT})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GethsVerdict extends Card
{
	public GethsVerdict(GameState state)
	{
		super(state);

		// Target player sacrifices a creature and loses 1 life.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(sacrifice(target, 1, CreaturePermanents.instance(), "Target player sacrifices a creature"));
		this.addEffect(loseLife(target, 1, "and loses 1 life."));
	}
}
