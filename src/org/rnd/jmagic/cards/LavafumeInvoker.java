package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Lavafume Invoker")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SHAMAN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class LavafumeInvoker extends Card
{
	public static final class LavafumeInvokerAbility0 extends ActivatedAbility
	{
		public LavafumeInvokerAbility0(GameState state)
		{
			super(state, "(8): Creatures you control get +3/+0 until end of turn.");
			this.setManaCost(new ManaPool("(8)"));

			this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +3, +0, "Creatures you control get +3/+0 until end of turn."));
		}
	}

	public LavafumeInvoker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (8): Creatures you control get +3/+0 until end of turn.
		this.addAbility(new LavafumeInvokerAbility0(state));
	}
}
