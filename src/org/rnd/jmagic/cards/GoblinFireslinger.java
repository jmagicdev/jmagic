package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Fireslinger")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinFireslinger extends Card
{
	public static final class GoblinFireslingerAbility0 extends ActivatedAbility
	{
		public GoblinFireslingerAbility0(GameState state)
		{
			super(state, "(T): Goblin Fireslinger deals 1 damage to target player.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(permanentDealDamage(1, target, "Goblin Fireslinger deals 1 damage to target player."));
		}
	}

	public GoblinFireslinger(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Goblin Fireslinger deals 1 damage to target player.
		this.addAbility(new GoblinFireslingerAbility0(state));
	}
}
