package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Eaten by Spiders")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class EatenbySpiders extends Card
{
	public EatenbySpiders(GameState state)
	{
		super(state);

		// Destroy target creature with flying and all Equipment attached to
		// that creature.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature with flying"));

		this.addEffect(destroy(Union.instance(target, Intersect.instance(HasSubType.instance(SubType.EQUIPMENT), AttachedTo.instance(target))), "Destroy target creature with flying and all Equipment attached to that creature."));
	}
}
