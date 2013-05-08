package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Frostburn Weird")
@Types({Type.CREATURE})
@SubTypes({SubType.WEIRD})
@ManaCost("(U/R)(U/R)")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class FrostburnWeird extends Card
{
	public static final class FrostburnWeirdAbility0 extends ActivatedAbility
	{
		public FrostburnWeirdAbility0(GameState state)
		{
			super(state, "(U/R): Frostburn Weird gets +1/-1 until end of turn.");
			this.setManaCost(new ManaPool("(U/R)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, -1, "Frostburn Weird gets +1/-1 until end of turn."));
		}
	}

	public FrostburnWeird(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// (u/r): Frostburn Weird gets +1/-1 until end of turn.
		this.addAbility(new FrostburnWeirdAbility0(state));
	}
}
