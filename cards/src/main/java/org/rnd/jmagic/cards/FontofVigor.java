package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Font of Vigor")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class FontofVigor extends Card
{
	public static final class FontofVigorAbility0 extends ActivatedAbility
	{
		public FontofVigorAbility0(GameState state)
		{
			super(state, "(2)(W), Sacrifice Font of Vigor: You gain 7 life.");
			this.setManaCost(new ManaPool("(2)(W)"));
			this.addCost(sacrificeThis("Font of Vigor"));
			this.addEffect(gainLife(You.instance(), 7, "You gain 7 life."));
		}
	}

	public FontofVigor(GameState state)
	{
		super(state);

		// (2)(W), Sacrifice Font of Vigor: You gain 7 life.
		this.addAbility(new FontofVigorAbility0(state));
	}
}
