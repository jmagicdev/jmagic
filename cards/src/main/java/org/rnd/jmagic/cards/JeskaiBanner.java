package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Jeskai Banner")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE})
public final class JeskaiBanner extends Card
{
	public static final class JeskaiBannerAbility1 extends ActivatedAbility
	{
		public JeskaiBannerAbility1(GameState state)
		{
			super(state, "(U)(R)(W), (T), Sacrifice Jeskai Banner: Draw a card.");
			this.setManaCost(new ManaPool("(U)(R)(W)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Jeskai Banner"));
			this.addEffect(drawACard());
		}
	}

	public JeskaiBanner(GameState state)
	{
		super(state);

		// (T): Add (U), (R), or (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(URW)"));

		// (U)(R)(W), (T), Sacrifice Jeskai Banner: Draw a card.
		this.addAbility(new JeskaiBannerAbility1(state));
	}
}
