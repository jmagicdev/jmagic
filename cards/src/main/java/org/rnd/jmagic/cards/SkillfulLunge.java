package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skillful Lunge")
@Types({Type.INSTANT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SkillfulLunge extends Card
{
	public SkillfulLunge(GameState state)
	{
		super(state);

		// Target creature gets +2/+0 and gains first strike until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +2, +0, "Target creature gets +2/+0 and gains first strike until end of turn.", org.rnd.jmagic.abilities.keywords.FirstStrike.class));
	}
}
