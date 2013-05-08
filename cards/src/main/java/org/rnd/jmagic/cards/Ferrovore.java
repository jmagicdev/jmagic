package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ferrovore")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Ferrovore extends Card
{
	public static final class FerrovoreAbility0 extends ActivatedAbility
	{
		public FerrovoreAbility0(GameState state)
		{
			super(state, "(R), Sacrifice an artifact: Ferrovore gets +3/+0 until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.addCost(sacrifice(You.instance(), 1, ArtifactPermanents.instance(), "Sacrifice an artifact"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +3, +0, "Ferrovore gets +3/+0 until end of turn."));
		}
	}

	public Ferrovore(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (R), Sacrifice an artifact: Ferrovore gets +3/+0 until end of turn.
		this.addAbility(new FerrovoreAbility0(state));
	}
}
