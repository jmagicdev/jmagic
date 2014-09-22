package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Akroan Phalanx")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("3W")
@ColorIdentity({Color.WHITE, Color.RED})
public final class AkroanPhalanx extends Card
{
	public static final class AkroanPhalanxAbility1 extends ActivatedAbility
	{
		public AkroanPhalanxAbility1(GameState state)
		{
			super(state, "(2)(R): Creatures you control get +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(2)(R)"));
			this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +0, "Creatures you control get +1/+0 until end of turn."));
		}
	}

	public AkroanPhalanx(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// (2)(R): Creatures you control get +1/+0 until end of turn.
		this.addAbility(new AkroanPhalanxAbility1(state));
	}
}
