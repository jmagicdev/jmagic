package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Drifting Shade")
@Types({Type.CREATURE})
@SubTypes({SubType.SHADE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DriftingShade extends Card
{
	public static final class DriftingShadeAbility1 extends ActivatedAbility
	{
		public DriftingShadeAbility1(GameState state)
		{
			super(state, "(B): Drifting Shade gets +1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(B)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Drifting Shade gets +1/+1 until end of turn."));
		}
	}

	public DriftingShade(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (B): Drifting Shade gets +1/+1 until end of turn.
		this.addAbility(new DriftingShadeAbility1(state));
	}
}
