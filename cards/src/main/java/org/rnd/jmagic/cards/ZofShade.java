package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Zof Shade")
@Types({Type.CREATURE})
@SubTypes({SubType.SHADE})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class ZofShade extends Card
{
	public static final class ZofShadeAbility0 extends ActivatedAbility
	{
		public ZofShadeAbility0(GameState state)
		{
			super(state, "(2)(B): Zof Shade gets +2/+2 until end of turn.");
			this.setManaCost(new ManaPool("(2)(B)"));
			this.addEffect(createFloatingEffect("Zof Shade gets +2/+2 until end of turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, +2, +2)));
		}
	}

	public ZofShade(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (2)(B): Zof Shade gets +2/+2 until end of turn.
		this.addAbility(new ZofShadeAbility0(state));
	}
}
