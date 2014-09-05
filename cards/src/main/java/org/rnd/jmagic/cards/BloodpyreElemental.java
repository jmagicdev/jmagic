package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bloodpyre Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BloodpyreElemental extends Card
{
	public static final class SloBurn extends ActivatedAbility
	{
		public SloBurn(GameState state)
		{
			super(state, "Sacrifice Bloodpyre Elemental: Bloodpyre Elemental deals 4 damage to target creature. Activate this ability only any time you could cast a sorcery.");

			this.addCost(sacrificeThis("Bloodpyre Elemental"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(permanentDealDamage(4, targetedBy(target), "Bloodpyre Elemental deals 4 damage to target creature."));

			this.activateOnlyAtSorcerySpeed();
		}
	}

	public BloodpyreElemental(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		this.addAbility(new SloBurn(state));
	}
}
