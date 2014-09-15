package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skyreaping")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class Skyreaping extends Card
{
	public Skyreaping(GameState state)
	{
		super(state);

		// Skyreaping deals damage to each creature with flying equal to your
		// devotion to green.
		SetGenerator hasFlying = Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
		this.addEffect(spellDealDamage(DevotionTo.instance(Color.GREEN), hasFlying, "Skyreaping deals damage to each creature with flying equal to your devotion to green."));
	}
}
