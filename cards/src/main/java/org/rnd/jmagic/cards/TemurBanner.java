package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Temur Banner")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({Color.RED, Color.BLUE, Color.GREEN})
public final class TemurBanner extends Card
{
	public static final class TemurBannerAbility1 extends ActivatedAbility
	{
		public TemurBannerAbility1(GameState state)
		{
			super(state, "(G)(U)(R), (T), Sacrifice Temur Banner: Draw a card.");
			this.setManaCost(new ManaPool("(G)(U)(R)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Temur Banner"));
			this.addEffect(drawACard());
		}
	}

	public TemurBanner(GameState state)
	{
		super(state);

		// (T): Add (G), (U), or (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GUR)"));

		// (G)(U)(R), (T), Sacrifice Temur Banner: Draw a card.
		this.addAbility(new TemurBannerAbility1(state));
	}
}
