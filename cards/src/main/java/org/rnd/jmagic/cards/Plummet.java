package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Plummet")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Plummet extends Card
{
	public Plummet(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature with flying"));

		// Destroy target creature with flying.
		this.addEffect(destroy(target, "Destroy target creature with flying."));
	}
}
