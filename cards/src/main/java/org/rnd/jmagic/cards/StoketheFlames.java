package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Stoke the Flames")
@Types({Type.INSTANT})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Magic2015CoreSet.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class StoketheFlames extends Card
{
	public StoketheFlames(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for {1} or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Stoke the Flames deals 4 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(4, targetedBy(target), "Stoke the Flames deals 4 damage to target creature or player."));
	}
}
