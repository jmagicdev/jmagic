package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sudden Impact")
@Types({Type.INSTANT})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class SuddenImpact extends Card
{
	public SuddenImpact(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		Count amount = Count.instance(InZone.instance(HandOf.instance(targetedBy(target))));
		this.addEffect(spellDealDamage(amount, targetedBy(target), "Sudden Impact deals damage to target player equal to the number of cards in that player's hand."));
	}
}
