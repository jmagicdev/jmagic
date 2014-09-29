package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Sultai Banner")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.GREEN})
public final class SultaiBanner extends Card
{
	public static final class SultaiBannerAbility1 extends ActivatedAbility
	{
		public SultaiBannerAbility1(GameState state)
		{
			super(state, "(B)(G)(U), (T), Sacrifice Sultai Banner: Draw a card.");
			this.setManaCost(new ManaPool("(B)(G)(U)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Sultai Banner"));
			this.addEffect(drawACard());
		}
	}

	public SultaiBanner(GameState state)
	{
		super(state);

		// (T): Add (B), (G), or (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BGU)"));

		// (B)(G)(U), (T), Sacrifice Sultai Banner: Draw a card.
		this.addAbility(new SultaiBannerAbility1(state));
	}
}
