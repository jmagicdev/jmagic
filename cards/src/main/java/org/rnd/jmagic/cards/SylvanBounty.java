package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sylvan Bounty")
@Types({Type.INSTANT})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class SylvanBounty extends Card
{
	public SylvanBounty(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(gainLife(targetedBy(target), 8, "Target player gains 8 life."));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.BasicLandCycling(state, "(1)(G)"));
	}
}
