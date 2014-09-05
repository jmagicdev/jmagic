package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Tainted Strike")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class TaintedStrike extends Card
{
	public TaintedStrike(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +0, "Target creature gets +1/+0 and gains infect until end of turn.", org.rnd.jmagic.abilities.keywords.Infect.class));
	}
}
