package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Feral Ridgewolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FeralRidgewolf extends Card
{
	public static final class FeralRidgewolfAbility1 extends ActivatedAbility
	{
		public FeralRidgewolfAbility1(GameState state)
		{
			super(state, "(1)(R): Feral Ridgewolf gets +2/+0 until end of turn.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "Feral Ridgewolf gets +2/+0 until end of turn."));
		}
	}

	public FeralRidgewolf(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// (1)(R): Feral Ridgewolf gets +2/+0 until end of turn.
		this.addAbility(new FeralRidgewolfAbility1(state));
	}
}
