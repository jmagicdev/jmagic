package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Colossal Might")
@Types({Type.INSTANT})
@ManaCost("RG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class ColossalMight extends Card
{
	public ColossalMight(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targetedBy(target), +4, +2, "Target creature gets +4/+2 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
	}
}
