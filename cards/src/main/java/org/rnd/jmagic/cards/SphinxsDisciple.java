package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Sphinx's Disciple")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class SphinxsDisciple extends Card
{
	public static final class SphinxsDiscipleAbility1 extends EventTriggeredAbility
	{
		public SphinxsDiscipleAbility1(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Sphinx's Disciple becomes untapped, draw a card.");
			this.addPattern(inspired());
			this.addEffect(drawACard());
		}
	}

	public SphinxsDisciple(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Inspired \u2014 Whenever Sphinx's Disciple becomes untapped, draw a
		// card.
		this.addAbility(new SphinxsDiscipleAbility1(state));
	}
}
