package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Drowned Rusalka")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class DrownedRusalka extends Card
{
	public static final class Drown extends ActivatedAbility
	{
		public Drown(GameState state)
		{
			super(state, "(U), Sacrifice a creature: Discard a card, then draw a card.");
			this.setManaCost(new ManaPool("U"));

			this.addCost(sacrificeACreature());
			this.addEffect(discardCards(You.instance(), 1, "Discard a card,"));
			this.addEffect(drawCards(You.instance(), 1, "then draw a card."));
		}
	}

	public DrownedRusalka(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (U), Sacrifice a creature: Discard a card, then draw a card.
		this.addAbility(new Drown(state));
	}
}
