package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Into the Maw of Hell")
@Types({Type.SORCERY})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class IntotheMawofHell extends Card
{
	public IntotheMawofHell(GameState state)
	{
		super(state);

		{
			// Destroy target land.
			SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
			this.addEffect(destroy(target, "Destroy target land."));
		}

		{
			// Into the Maw of Hell deals 13 damage to target creature.
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(spellDealDamage(13, target, "Into the Maw of Hell deals 13 damage to target creature."));
		}
	}
}
