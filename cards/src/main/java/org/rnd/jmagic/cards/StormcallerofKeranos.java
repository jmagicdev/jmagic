package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Stormcaller of Keranos")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class StormcallerofKeranos extends Card
{
	public static final class StormcallerofKeranosAbility1 extends ActivatedAbility
	{
		public StormcallerofKeranosAbility1(GameState state)
		{
			super(state, "(1)(U): Scry 1.");
			this.setManaCost(new ManaPool("(1)(U)"));
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public StormcallerofKeranos(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// (1)(U): Scry 1. (Look at the top card of your library. You may put
		// that card on the bottom of your library.)
		this.addAbility(new StormcallerofKeranosAbility1(state));
	}
}
