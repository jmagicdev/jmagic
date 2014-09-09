package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kindled Fury")
@Types({Type.INSTANT})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class KindledFury extends Card
{
	public KindledFury(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targetedBy(target), +1, +0, "Target creature gets +1/+0 and gains first strike until end of turn.", org.rnd.jmagic.abilities.keywords.FirstStrike.class));
	}
}
