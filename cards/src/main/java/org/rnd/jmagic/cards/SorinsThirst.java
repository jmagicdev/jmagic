package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sorin's Thirst")
@Types({Type.INSTANT})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SorinsThirst extends Card
{
	public SorinsThirst(GameState state)
	{
		super(state);

		// Sorin's Thirst deals 2 damage to target creature and you gain 2 life.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(2, target, "Sorin's Thirst deals 2 damage to target creature"));
		this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));
	}
}
