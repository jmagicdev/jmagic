package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Grenade")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FALLEN_EMPIRES, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinGrenade extends Card
{
	public GoblinGrenade(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");
		this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.GOBLIN), "sacrifice a Goblin"));
		this.addEffect(spellDealDamage(5, targetedBy(target), "Goblin Grenade deals 5 damage to target player."));
	}
}
