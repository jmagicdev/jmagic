package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Cavern Thoctar")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("5G")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class CavernThoctar extends Card
{
	public static final class ExpensiveFirebreathing extends ActivatedAbility
	{
		public ExpensiveFirebreathing(GameState state)
		{
			super(state, "(1)(R): Cavern Thoctar gets +1/+0 until end of turn.");

			this.setManaCost(new ManaPool("1R"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+1), (+0), "Cavern Thoctar gets +1/+0 until end of turn."));
		}
	}

	public CavernThoctar(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// (1)(R): Cavern Thoctar gets +1/+0 until end of turn.
		this.addAbility(new ExpensiveFirebreathing(state));
	}
}
