package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Slingbow Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class SlingbowTrap extends Card
{
	public SlingbowTrap(GameState state)
	{
		super(state);

		SetGenerator targetingRestriction = Intersect.instance(Attacking.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));

		// If a black creature with flying is attacking, you may pay (G) rather
		// than pay Slingbow Trap's mana cost.
		SetGenerator blackAttackingWithFlying = Intersect.instance(HasColor.instance(Color.BLACK), targetingRestriction);
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), blackAttackingWithFlying, "If a black creature with flying is attacking", "(G)"));

		// Destroy target attacking creature with flying.
		SetGenerator target = targetedBy(this.addTarget(targetingRestriction, "target attacking creature with flying"));
		this.addEffect(destroy(target, "Destroy target attacking creature with flying."));
	}
}
