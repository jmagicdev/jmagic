package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tendrils of Corruption")
@Types({Type.INSTANT})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class TendrilsofCorruption extends Card
{
	public TendrilsofCorruption(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		SetGenerator X = Count.instance(Intersect.instance(HasSubType.instance(SubType.SWAMP), ControlledBy.instance(You.instance())));
		this.addEffect(spellDealDamage(X, targetedBy(target), "Tendrils of Corruption deals X damage to target creature"));
		this.addEffect(gainLife(You.instance(), X, "and you gain X life, where X is the number of Swamps you control."));
	}
}
