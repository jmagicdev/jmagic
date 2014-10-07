package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Font of Ire")
@Types({Type.ENCHANTMENT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class FontofIre extends Card
{
	public static final class FontofIreAbility0 extends ActivatedAbility
	{
		public FontofIreAbility0(GameState state)
		{
			super(state, "(3)(R), Sacrifice Font of Ire: Font of Ire deals 5 damage to target player.");
			this.setManaCost(new ManaPool("(3)(R)"));
			this.addCost(sacrificeThis("Font of Ire"));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(permanentDealDamage(5, target, "Font of Ire deals 5 damage to target player."));
		}
	}

	public FontofIre(GameState state)
	{
		super(state);

		// (3)(R), Sacrifice Font of Ire: Font of Ire deals 5 damage to target
		// player.
		this.addAbility(new FontofIreAbility0(state));
	}
}
