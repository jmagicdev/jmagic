package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crushing Vines")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class CrushingVines extends Card
{
	public CrushingVines(GameState state)
	{
		super(state);

		// Choose one \u2014 Destroy target creature with flying; or destroy
		// target artifact.
		{
			SetGenerator creatures = Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			SetGenerator target = targetedBy(this.addTarget(1, creatures, "target creature with flying"));
			this.addEffect(1, destroy(target, "Destroy target creature with flying"));
		}
		{
			SetGenerator target = targetedBy(this.addTarget(2, ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(2, destroy(target, "destroy target artifact."));
		}
	}
}
