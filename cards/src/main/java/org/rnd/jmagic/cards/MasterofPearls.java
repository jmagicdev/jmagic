package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Master of Pearls")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class MasterofPearls extends Card
{
	public static final class MasterofPearlsAbility1 extends EventTriggeredAbility
	{
		public MasterofPearlsAbility1(GameState state)
		{
			super(state, "When Master of Pearls is turned face up, creatures you control get +2/+2 until end of turn.");
			this.addPattern(whenThisIsTurnedFaceUp());
			this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +2, +2, "Creatures you control get +2/+2 until end of turn."));
		}
	}

	public MasterofPearls(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Morph (3)(W)(W) (You may cast this card face down as a 2/2 creature
		// for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(3)(W)(W)"));

		// When Master of Pearls is turned face up, creatures you control get
		// +2/+2 until end of turn.
		this.addAbility(new MasterofPearlsAbility1(state));
	}
}
