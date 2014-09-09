package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Jayemdae Tome")
@Types({Type.ARTIFACT})
@ManaCost("4")
@ColorIdentity({})
public final class JayemdaeTome extends Card
{
	public static final class DrawOne extends ActivatedAbility
	{
		public DrawOne(GameState state)
		{
			super(state, "(4), (T): Draw a card.");

			this.setManaCost(new ManaPool("4"));

			this.costsTap = true;

			this.addEffect(drawACard());
		}
	}

	public JayemdaeTome(GameState state)
	{
		super(state);

		this.addAbility(new DrawOne(state));
	}
}
