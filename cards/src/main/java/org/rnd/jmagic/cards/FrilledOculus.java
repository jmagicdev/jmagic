package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Frilled Oculus")
@Types({Type.CREATURE})
@SubTypes({SubType.HOMUNCULUS})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class FrilledOculus extends Card
{
	public static final class FrilledOculusAbility0 extends ActivatedAbility
	{
		public FrilledOculusAbility0(GameState state)
		{
			super(state, "(1)(G): Frilled Oculus gets +2/+2 until end of turn. Activate this ability only once each turn.");
			this.setManaCost(new ManaPool("(1)(G)"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "Frilled Oculus gets +2/+2 until end of turn."));

			this.perTurnLimit(1);
		}
	}

	public FrilledOculus(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// (1)(G): Frilled Oculus gets +2/+2 until end of turn. Activate this
		// ability only once each turn.
		this.addAbility(new FrilledOculusAbility0(state));
	}
}
