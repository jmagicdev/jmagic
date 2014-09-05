package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Watercourser")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Watercourser extends Card
{
	public static final class WatercourserAbility0 extends ActivatedAbility
	{
		public WatercourserAbility0(GameState state)
		{
			super(state, "(U): Watercourser gets +1/-1 until end of turn.");
			this.setManaCost(new ManaPool("(U)"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, -1, "Watercourser gets +1/-1 until end of turn."));
		}
	}

	public Watercourser(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// (U): Watercourser gets +1/-1 until end of turn.
		this.addAbility(new WatercourserAbility0(state));
	}
}
