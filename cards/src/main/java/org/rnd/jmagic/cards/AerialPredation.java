package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aerial Predation")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class AerialPredation extends Card
{
	public AerialPredation(GameState state)
	{
		super(state);

		// Destroy target creature with flying. You gain 2 life.
		SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
		SetGenerator creaturesWithFlying = Intersect.instance(CreaturePermanents.instance(), hasFlying);
		SetGenerator target = targetedBy(this.addTarget(creaturesWithFlying, "target creature with flying"));
		this.addEffect(destroy(target, "Destroy target creature with flying."));

		this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
	}
}
