package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Bellows Lizard")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BellowsLizard extends Card
{
	public static final class BellowsLizardAbility0 extends ActivatedAbility
	{
		public BellowsLizardAbility0(GameState state)
		{
			super(state, "(1)(R): Bellows Lizard gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Bellows Lizard gets +1/+0 until end of turn."));
		}
	}

	public BellowsLizard(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1)(R): Bellows Lizard gets +1/+0 until end of turn.
		this.addAbility(new BellowsLizardAbility0(state));
	}
}
