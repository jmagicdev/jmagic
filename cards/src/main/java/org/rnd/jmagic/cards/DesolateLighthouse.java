package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Desolate Lighthouse")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class DesolateLighthouse extends Card
{
	public static final class DesolateLighthouseAbility1 extends ActivatedAbility
	{
		public DesolateLighthouseAbility1(GameState state)
		{
			super(state, "(1)(U)(R), (T): Draw a card, then discard a card.");
			this.setManaCost(new ManaPool("(1)(U)(R)"));
			this.costsTap = true;

			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));
			this.addEffect(discardCards(You.instance(), 1, "then discard a card."));
		}
	}

	public DesolateLighthouse(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1)(U)(R), (T): Draw a card, then discard a card.
		this.addAbility(new DesolateLighthouseAbility1(state));
	}
}
