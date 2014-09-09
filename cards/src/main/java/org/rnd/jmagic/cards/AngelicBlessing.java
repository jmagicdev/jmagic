package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angelic Blessing")
@Types({Type.SORCERY})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class AngelicBlessing extends Card
{
	public AngelicBlessing(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targetedBy(target), +3, +3, "Target creature gets +3/+3 and gains flying until end of turn.", org.rnd.jmagic.abilities.keywords.Flying.class));
	}
}
