package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Warmind Infantry")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL, SubType.SOLDIER})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class WarmindInfantry extends Card
{
	public static final class WarmindInfantryAbility0 extends EventTriggeredAbility
	{
		public WarmindInfantryAbility0(GameState state)
		{
			super(state, "Whenever Warmind Infantry and at least two other creatures attack, Warmind Infantry gets +2/+0 until end of turn.");
			this.addPattern(battalion());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "Warmind Infantry gets +2/+0 until end of turn."));
		}
	}

	public WarmindInfantry(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Battalion \u2014 Whenever Warmind Infantry and at least two other
		// creatures attack, Warmind Infantry gets +2/+0 until end of turn.
		this.addAbility(new WarmindInfantryAbility0(state));
	}
}
