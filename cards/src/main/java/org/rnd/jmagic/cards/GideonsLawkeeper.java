package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gideon's Lawkeeper")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GideonsLawkeeper extends Card
{
	public static final class GideonsLawkeeperAbility0 extends ActivatedAbility
	{
		public GideonsLawkeeperAbility0(GameState state)
		{
			super(state, "(W), (T): Tap target creature.");
			this.setManaCost(new ManaPool("(W)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(tap(target, "Tap target creature."));
		}
	}

	public GideonsLawkeeper(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (W), (T): Tap target creature.
		this.addAbility(new GideonsLawkeeperAbility0(state));
	}
}
