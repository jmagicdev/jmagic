package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Frostling")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class Frostling extends Card
{
	public static final class SacPing extends ActivatedAbility
	{
		public SacPing(GameState state)
		{
			super(state, "Sacrifice Frostling: Frostling deals 1 damage to target creature.");
			this.addCost(sacrificeThis("Frostling"));
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(permanentDealDamage(1, targetedBy(target), "Frostling deals 1 damage to target creature."));
		}
	}

	public Frostling(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new SacPing(state));
	}
}
