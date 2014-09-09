package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tower of Calamities")
@Types({Type.ARTIFACT})
@ManaCost("4")
@ColorIdentity({})
public final class TowerofCalamities extends Card
{
	public static final class TowerofCalamitiesAbility0 extends ActivatedAbility
	{
		public TowerofCalamitiesAbility0(GameState state)
		{
			super(state, "(8), (T): Tower of Calamities deals 12 damage to target creature.");
			this.setManaCost(new ManaPool("(8)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(12, target, "Tower of Calamities deals 12 damage to target creature."));
		}
	}

	public TowerofCalamities(GameState state)
	{
		super(state);

		// (8), (T): Tower of Calamities deals 12 damage to target creature.
		this.addAbility(new TowerofCalamitiesAbility0(state));
	}
}
