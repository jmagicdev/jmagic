package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Slagstorm")
@Types({Type.SORCERY})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Slagstorm extends Card
{
	public Slagstorm(GameState state)
	{
		super(state);

		// Choose one \u2014 Slagstorm deals 3 damage to each creature; or
		// Slagstorm deals 3 damage to each player.

		this.addEffect(1, spellDealDamage(3, CreaturePermanents.instance(), "Slagstorm deals 3 damage to each creature."));
		this.addEffect(2, spellDealDamage(3, Players.instance(), "Slagstorm deals 3 damage to each player."));
	}
}
