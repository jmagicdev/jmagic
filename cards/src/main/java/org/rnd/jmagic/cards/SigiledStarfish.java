package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Sigiled Starfish")
@Types({Type.CREATURE})
@SubTypes({SubType.STARFISH})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class SigiledStarfish extends Card
{
	public static final class SigiledStarfishAbility0 extends ActivatedAbility
	{
		public SigiledStarfishAbility0(GameState state)
		{
			super(state, "(T): Scry 1.");
			this.costsTap = true;
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public SigiledStarfish(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// (T): Scry 1. (Look at the top card of your library. You may put that
		// card on the bottom of your library.)
		this.addAbility(new SigiledStarfishAbility0(state));
	}
}
