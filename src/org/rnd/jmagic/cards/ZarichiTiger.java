package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Zarichi Tiger")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ZarichiTiger extends Card
{
	public static final class ZarichiTigerAbility0 extends ActivatedAbility
	{
		public ZarichiTigerAbility0(GameState state)
		{
			super(state, "(1)(W), (T): You gain 2 life.");
			this.setManaCost(new ManaPool("(1)(W)"));
			this.costsTap = true;
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public ZarichiTiger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// (1)(W), (T): You gain 2 life.
		this.addAbility(new ZarichiTigerAbility0(state));
	}
}
