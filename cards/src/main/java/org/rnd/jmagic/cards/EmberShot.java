package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ember Shot")
@Types({Type.INSTANT})
@ManaCost("6R")
@Printings({@Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class EmberShot extends Card
{
	public EmberShot(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(3, targetedBy(target), "Ember Shot deals 3 damage to target creature or player."));
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
