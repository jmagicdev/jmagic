package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Slayers' Stronghold")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class SlayersStronghold extends Card
{
	public static final class SlayersStrongholdAbility1 extends ActivatedAbility
	{
		public SlayersStrongholdAbility1(GameState state)
		{
			super(state, "(R)(W), (T): Target creature gets +2/+0 and gains vigilance and haste until end of turn.");
			this.setManaCost(new ManaPool("(R)(W)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +2, +0, "Target creature gets +2/+0 and gains vigilance and haste until end of turn.", org.rnd.jmagic.abilities.keywords.Vigilance.class, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public SlayersStronghold(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (R)(W), (T): Target creature gets +2/+0 and gains vigilance and haste
		// until end of turn.
		this.addAbility(new SlayersStrongholdAbility1(state));
	}
}
