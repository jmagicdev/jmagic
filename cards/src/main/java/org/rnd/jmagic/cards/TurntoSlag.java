package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Turn to Slag")
@Types({Type.SORCERY})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class TurntoSlag extends Card
{
	public TurntoSlag(GameState state)
	{
		super(state);

		// Turn to Slag deals 5 damage to target creature. Destroy all Equipment
		// attached to that creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(5, target, "Turn to Slag deals 5 damage to target creature."));
		SetGenerator destroy = Intersect.instance(AttachedTo.instance(target), HasSubType.instance(SubType.EQUIPMENT));
		this.addEffect(destroy(destroy, "Destroy all Equipment attached to that creature."));
	}
}
