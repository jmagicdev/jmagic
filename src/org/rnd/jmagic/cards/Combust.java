package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Combust")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Combust extends Card
{
	public Combust(GameState state)
	{
		super(state);

		// Combust can't be countered by spells or abilities.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// Combust deals 5 damage to target white or blue creature. The damage
		// can't be prevented.
		SetGenerator legalTargets = Intersect.instance(HasColor.instance(Color.WHITE, Color.BLUE), CreaturePermanents.instance());
		SetGenerator target = targetedBy(this.addTarget(legalTargets, "target white or blue creature"));

		EventFactory damage = spellDealDamage(5, target, "Combust deals 5 damage to target white or blue creature. The damage can't be prevented.");
		damage.parameters.put(EventType.Parameter.PREVENT, Empty.instance());
		this.addEffect(damage);
	}
}
