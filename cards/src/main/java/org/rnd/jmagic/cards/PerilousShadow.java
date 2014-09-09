package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Perilous Shadow")
@Types({Type.CREATURE})
@SubTypes({SubType.SHADE, SubType.INSECT})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class PerilousShadow extends Card
{
	public static final class PerilousShadowAbility0 extends ActivatedAbility
	{
		public PerilousShadowAbility0(GameState state)
		{
			super(state, "(1)(B): Perilous Shadow gets +2/+2 until end of turn.");
			this.setManaCost(new ManaPool("(1)(B)"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "Perilous Shadow gets +2/+2 until end of turn."));
		}
	}

	public PerilousShadow(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// (1)(B): Perilous Shadow gets +2/+2 until end of turn.
		this.addAbility(new PerilousShadowAbility0(state));
	}
}
