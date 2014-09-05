package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Thunderous Wrath")
@Types({Type.INSTANT})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ThunderousWrath extends Card
{
	public ThunderousWrath(GameState state)
	{
		super(state);

		// Thunderous Wrath deals 5 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(5, targetedBy(target), "Thunderous Wrath deals 5 damage to target creature or player."));

		// Miracle (R)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Miracle(state, "(R)"));
	}
}
