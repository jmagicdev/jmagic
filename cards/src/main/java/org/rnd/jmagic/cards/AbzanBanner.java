package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Abzan Banner")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class AbzanBanner extends Card
{
	public static final class AbzanBannerAbility1 extends ActivatedAbility
	{
		public AbzanBannerAbility1(GameState state)
		{
			super(state, "(W)(B)(G), (T), Sacrifice Abzan Banner: Draw a card.");
			this.setManaCost(new ManaPool("(W)(B)(G)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Abzan Banner"));
			this.addEffect(drawACard());
		}
	}

	public AbzanBanner(GameState state)
	{
		super(state);

		// (T): Add (W), (B), or (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WBG)"));

		// (W)(B)(G), (T), Sacrifice Abzan Banner: Draw a card.
		this.addAbility(new AbzanBannerAbility1(state));
	}
}
