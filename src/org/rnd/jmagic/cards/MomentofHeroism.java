package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Moment of Heroism")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class MomentofHeroism extends Card
{
	public MomentofHeroism(GameState state)
	{
		super(state);

		// Target creature gets +2/+2 and gains lifelink until end of turn.
		// (Damage dealt by the creature also causes its controller to gain that
		// much life.)
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +2, +2, "Target creature gets +2/+2 and gains lifelink until end of turn.", org.rnd.jmagic.abilities.keywords.Lifelink.class));
	}
}
