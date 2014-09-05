package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rummaging Goblin")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.ROGUE})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RummagingGoblin extends Card
{
	public static final class RummagingGoblinAbility0 extends ActivatedAbility
	{
		public RummagingGoblinAbility0(GameState state)
		{
			super(state, "(T), Discard a card: Draw a card.");
			this.costsTap = true;
			// Discard a card
			this.addCost(discardCards(You.instance(), 1, "Discard a card"));
			this.addEffect(drawACard());
		}
	}

	public RummagingGoblin(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T), Discard a card: Draw a card.
		this.addAbility(new RummagingGoblinAbility0(state));
	}
}
