package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cephalid Coliseum")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Odyssey.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class CephalidColiseum extends Card
{
	public static final class DrawThreeDiscardThree extends ActivatedAbility
	{
		public DrawThreeDiscardThree(GameState state)
		{
			super(state, "(U), (T), Sacrifice Cephalid Coliseum: Target player draws three cards, then discards three cards. Activate this ability only if seven or more cards are in your graveyard.");
			this.setManaCost(new ManaPool("U"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Cephalid Coliseum"));

			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(drawCards(targetedBy(target), 3, "Target player draws three cards,"));
			this.addEffect(discardCards(targetedBy(target), 3, "then discards three cards."));
			this.addActivateRestriction(Not.instance(Threshold.instance()));
		}
	}

	public CephalidColiseum(GameState state)
	{
		super(state);

		// (T): Add (U) to your mana pool. Cephalid Coliseum deals 1 damage to
		// you.
		this.addAbility(new org.rnd.jmagic.abilities.TapForManaPain(state, this.getName(), "U"));

		// Threshold \u2014 (U), (T), Sacrifice Cephalid Coliseum: Target player
		// draws three cards, then discards three cards. Activate this ability
		// only if seven or more cards are in your graveyard.
		this.addAbility(new DrawThreeDiscardThree(state));
	}
}
