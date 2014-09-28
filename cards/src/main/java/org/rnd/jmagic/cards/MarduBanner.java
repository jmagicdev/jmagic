package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Mardu Banner")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLACK})
public final class MarduBanner extends Card
{
	public static final class MarduBannerAbility1 extends ActivatedAbility
	{
		public MarduBannerAbility1(GameState state)
		{
			super(state, "(R)(W)(B), (T), Sacrifice Mardu Banner: Draw a card.");
			this.setManaCost(new ManaPool("(R)(W)(B)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Mardu Banner"));
		}
	}

	public MarduBanner(GameState state)
	{
		super(state);

		// (T): Add (R), (W), or (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(RWB)"));

		// (R)(W)(B), (T), Sacrifice Mardu Banner: Draw a card.
		this.addAbility(new MarduBannerAbility1(state));
	}
}
