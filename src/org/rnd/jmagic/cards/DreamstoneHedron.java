package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dreamstone Hedron")
@Types({Type.ARTIFACT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DreamstoneHedron extends Card
{
	public static final class DreamstoneHedronAbility0 extends ActivatedAbility
	{
		public DreamstoneHedronAbility0(GameState state)
		{
			super(state, "(T): Add (3) to your mana pool.");

			this.costsTap = true;

			this.addEffect(addManaToYourManaPoolFromAbility("(3)"));
		}
	}

	public static final class DreamstoneHedronAbility1 extends ActivatedAbility
	{
		public DreamstoneHedronAbility1(GameState state)
		{
			super(state, "(3), (T), Sacrifice Dreamstone Hedron: Draw three cards.");

			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Dreamstone Hedron"));

			this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));
		}
	}

	public DreamstoneHedron(GameState state)
	{
		super(state);

		// (T): Add (3) to your mana pool.
		this.addAbility(new DreamstoneHedronAbility0(state));

		// (3), (T), Sacrifice Dreamstone Hedron: Draw three cards.
		this.addAbility(new DreamstoneHedronAbility1(state));
	}
}
