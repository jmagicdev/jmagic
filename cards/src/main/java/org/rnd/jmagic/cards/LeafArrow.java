package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Leaf Arrow")
@Types({Type.INSTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class LeafArrow extends Card
{
	public LeafArrow(GameState state)
	{
		super(state);

		// Leaf Arrow deals 3 damage to target creature with flying.
		Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature with flying");

		this.addEffect(spellDealDamage(3, targetedBy(target), "Leaf Arrow deals 3 damage to target creature with flying."));
	}
}
