package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Razortip Whip")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class RazortipWhip extends Card
{
	public static final class RazortipWhipAbility0 extends ActivatedAbility
	{
		public RazortipWhipAbility0(GameState state)
		{
			super(state, "(1), (T): Razortip Whip deals 1 damage to target opponent.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(permanentDealDamage(1, target, "Razortip Whip deals 1 damage to target opponent."));
		}
	}

	public RazortipWhip(GameState state)
	{
		super(state);

		// (1), (T): Razortip Whip deals 1 damage to target opponent.
		this.addAbility(new RazortipWhipAbility0(state));
	}
}
