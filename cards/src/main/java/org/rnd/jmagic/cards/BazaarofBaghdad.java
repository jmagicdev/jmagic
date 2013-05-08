package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bazaar of Baghdad")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.ARABIAN_NIGHTS, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class BazaarofBaghdad extends Card
{
	public static final class BazaarofBaghdadAbility0 extends ActivatedAbility
	{
		public BazaarofBaghdadAbility0(GameState state)
		{
			super(state, "(T): Draw two cards, then discard three cards.");
			this.costsTap = true;
			this.addEffect(drawCards(You.instance(), 2, "Draw two cards,"));
			this.addEffect(discardCards(You.instance(), 3, "then discard three cards."));
		}
	}

	public BazaarofBaghdad(GameState state)
	{
		super(state);

		// (T): Draw two cards, then discard three cards.
		this.addAbility(new BazaarofBaghdadAbility0(state));
	}
}
