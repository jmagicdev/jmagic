package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elder of Laurels")
@Types({Type.CREATURE})
@SubTypes({SubType.ADVISOR, SubType.HUMAN})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ElderofLaurels extends Card
{
	public static final class ElderofLaurelsAbility0 extends ActivatedAbility
	{
		public ElderofLaurelsAbility0(GameState state)
		{
			super(state, "(3)(G): Target creature gets +X/+X until end of turn, where X is the number of creatures you control.");
			this.setManaCost(new ManaPool("(3)(G)"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			SetGenerator amount = Count.instance(CREATURES_YOU_CONTROL);
			this.addEffect(ptChangeUntilEndOfTurn(target, amount, amount, "Target creature gets +X/+X until end of turn, where X is the number of creatures you control."));
		}
	}

	public ElderofLaurels(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// (3)(G): Target creature gets +X/+X until end of turn, where X is the
		// number of creatures you control.
		this.addAbility(new ElderofLaurelsAbility0(state));
	}
}
