package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thunderbolt")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class Thunderbolt extends Card
{
	public Thunderbolt(GameState state)
	{
		super(state);

		// Choose one \u2014 Thunderbolt deals 3 damage to target player; or
		// Thunderbolt deals 4 damage to target creature with flying.
		{
			SetGenerator target = targetedBy(this.addTarget(1, Players.instance(), "target player"));
			this.addEffect(1, spellDealDamage(3, target, "Thunderbolt deals 3 damage to target player."));
		}
		{
			SetGenerator legal = Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			SetGenerator target = targetedBy(this.addTarget(2, legal, "target creature with flying"));
			this.addEffect(2, spellDealDamage(4, target, "Thunderbolt deals 4 damage to target creature with flying."));
		}
	}
}
