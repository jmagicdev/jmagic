package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Mind Stone")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class MindStone extends Card
{
	public static final class Brainfood extends ActivatedAbility
	{
		public Brainfood(GameState state)
		{
			super(state, "(1), (T), Sacrifice Mind Stone: Draw a card.");

			this.setManaCost(new ManaPool("1"));

			this.costsTap = true;

			this.addCost(sacrificeThis("Mind Stone"));

			this.addEffect(drawACard());
		}
	}

	public MindStone(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		this.addAbility(new Brainfood(state));
	}
}
