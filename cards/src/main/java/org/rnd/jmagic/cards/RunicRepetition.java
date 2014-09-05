package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Runic Repetition")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class RunicRepetition extends Card
{
	public RunicRepetition(GameState state)
	{
		super(state);

		// Return target exiled card with flashback you own to your hand.
		SetGenerator targetingRestriction = Intersect.instance(InZone.instance(ExileZone.instance()), Cards.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flashback.class), OwnedBy.instance(You.instance()));
		SetGenerator target = targetedBy(this.addTarget(targetingRestriction, "target exiled card with flashback you own"));
		this.addEffect(putIntoHand(target, You.instance(), "Return target exiled card with flashback you own to your hand."));
	}
}
