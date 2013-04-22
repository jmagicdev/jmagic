package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bituminous Blast")
@Types({Type.INSTANT})
@ManaCost("3BR")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class BituminousBlast extends Card
{
	public BituminousBlast(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cascade(state));

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(spellDealDamage(4, targetedBy(target), "Bituminous Blast deals 4 damage to target creature."));
	}
}
