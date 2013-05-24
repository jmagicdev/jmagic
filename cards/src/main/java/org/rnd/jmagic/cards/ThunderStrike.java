package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thunder Strike")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ThunderStrike extends Card
{
	public ThunderStrike(GameState state)
	{
		super(state);

		// Target creature gets +2/+0 and gains first strike until end of turn.
		// (It deals combat damage before creatures without first strike.)
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "Target creature"));
		ContinuousEffect.Part plusTwoPlusZero = modifyPowerAndToughness(target, +2, +0);
		ContinuousEffect.Part firstStrike = addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.FirstStrike.class);
		this.addEffect(createFloatingEffect("Target creature gets +2/+0 and gains first strike until end of turn.", plusTwoPlusZero, firstStrike));
	}
}
