package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Sigiled Skink")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class SigiledSkink extends Card
{
	public static final class SigiledSkinkAbility0 extends EventTriggeredAbility
	{
		public SigiledSkinkAbility0(GameState state)
		{
			super(state, "Whenever Sigiled Skink attacks, scry 1.");
			this.addPattern(whenThisAttacks());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public SigiledSkink(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever Sigiled Skink attacks, scry 1. (Look at the top card of your
		// library. You may put that card on the bottom of your library.)
		this.addAbility(new SigiledSkinkAbility0(state));
	}
}
