package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Branching Bolt")
@Types({Type.INSTANT})
@ManaCost("1RG")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class BranchingBolt extends Card
{
	public BranchingBolt(GameState state)
	{
		super(state);

		this.setNumModes(new Set(new org.rnd.util.NumberRange(1, 2)));

		// Choose one or both \u2014 Branching Bolt deals 3 damage to target
		// creature with flying; and/or Branching Bolt deals 3 damage to target
		// creature without flying.
		{
			Target target = this.addTarget(1, Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature with flying");
			this.addEffect(1, spellDealDamage(3, targetedBy(target), "Branching Bolt deals 3 damage to target creature with flying."));
		}

		{
			Target target = this.addTarget(2, RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target creature without flying");
			this.addEffect(2, spellDealDamage(3, targetedBy(target), "Branching Bolt deals 3 damage to target creature without flying."));
		}
	}
}
