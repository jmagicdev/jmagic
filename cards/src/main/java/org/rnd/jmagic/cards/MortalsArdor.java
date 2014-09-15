package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mortal's Ardor")
@Types({Type.INSTANT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class MortalsArdor extends Card
{
	public MortalsArdor(GameState state)
	{
		super(state);

		// Target creature gets +1/+1 and gains lifelink until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +1, "Target creature gets +1/+1 and gains lifelink until end of turn.", org.rnd.jmagic.abilities.keywords.Lifelink.class));
	}
}
