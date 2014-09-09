package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sphinx of Magosi")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("3UUU")
@ColorIdentity({Color.BLUE})
public final class SphinxofMagosi extends Card
{
	public static final class DrawAndPump extends ActivatedAbility
	{
		public DrawAndPump(GameState state)
		{
			super(state, "(2)(U): Draw a card, then put a +1/+1 counter on Sphinx of Magosi.");
			this.setManaCost(new ManaPool("2U"));

			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "then put a +1/+1 counter on Sphinx of Magosi."));
		}
	}

	public SphinxofMagosi(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (2)(U): Draw a card, then put a +1/+1 counter on Sphinx of Magosi.
		this.addAbility(new DrawAndPump(state));
	}
}
