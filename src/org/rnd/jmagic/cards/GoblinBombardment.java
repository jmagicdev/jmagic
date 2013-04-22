package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Goblin Bombardment")
@Types({Type.ENCHANTMENT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class GoblinBombardment extends Card
{
	public static final class GoblinBombardmentAbility0 extends ActivatedAbility
	{
		public GoblinBombardmentAbility0(GameState state)
		{
			super(state, "Sacrifice a creature: Goblin Bombardment deals 1 damage to target creature or player.");
			this.addCost(sacrificeACreature());
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(1, target, "Goblin Bombardment deals 1 damage to target creature or player."));
		}
	}

	public GoblinBombardment(GameState state)
	{
		super(state);

		// Sacrifice a creature: Goblin Bombardment deals 1 damage to target
		// creature or player.
		this.addAbility(new GoblinBombardmentAbility0(state));
	}
}
