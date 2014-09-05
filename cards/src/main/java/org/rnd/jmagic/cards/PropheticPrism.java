package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Prophetic Prism")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON), @Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class PropheticPrism extends Card
{
	public static final class ETBDraw extends EventTriggeredAbility
	{
		public ETBDraw(GameState state)
		{
			super(state, "When Prophetic Prism enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public static final class PropheticPrismAbility1 extends org.rnd.jmagic.abilities.TapForMana
	{
		public PropheticPrismAbility1(GameState state)
		{
			super(state, "(WUBRG)");
			this.setName("(1), (T): Add one mana of any color to your mana pool.");
			this.setManaCost(new ManaPool("1"));
		}
	}

	public PropheticPrism(GameState state)
	{
		super(state);

		// When Prophetic Prism enters the battlefield, draw a card.
		this.addAbility(new ETBDraw(state));

		// (1), (T): Add one mana of any color to your mana pool.
		this.addAbility(new PropheticPrismAbility1(state));
	}
}
