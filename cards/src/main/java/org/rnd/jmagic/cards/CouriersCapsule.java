package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Courier's Capsule")
@Types({Type.ARTIFACT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CouriersCapsule extends Card
{
	public static final class BluePill extends ActivatedAbility
	{
		public BluePill(GameState state)
		{
			super(state, "(1)(U), (T), Sacrifice Courier's Capsule: Draw two cards.");

			this.setManaCost(new ManaPool("1U"));

			this.costsTap = true;

			this.addCost(sacrificeThis("Courier's Capsule"));

			this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
		}
	}

	public CouriersCapsule(GameState state)
	{
		super(state);

		this.addAbility(new BluePill(state));
	}
}
