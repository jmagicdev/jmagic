package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Font of Fortunes")
@Types({Type.ENCHANTMENT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class FontofFortunes extends Card
{
	public static final class FontofFortunesAbility0 extends ActivatedAbility
	{
		public FontofFortunesAbility0(GameState state)
		{
			super(state, "(1)(U), Sacrifice Font of Fortunes: Draw two cards.");
			this.setManaCost(new ManaPool("1U"));
			this.addCost(sacrificeThis("Font of Fortunes"));
			this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
		}
	}

	public FontofFortunes(GameState state)
	{
		super(state);

		// {1}{U}, Sacrifice Font of Fortunes: Draw two cards.
		this.addAbility(new FontofFortunesAbility0(state));
	}
}
