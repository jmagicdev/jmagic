package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Identity Crisis")
@Types({Type.SORCERY})
@ManaCost("2WWBB")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class IdentityCrisis extends Card
{
	public IdentityCrisis(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		// TODO : This doesn't work. Convenience.exile passes a ZoneContaining
		// to MOVE_OBJECTS.
		this.addEffect(exile(InZone.instance(Union.instance(HandOf.instance(targetedBy(target)), GraveyardOf.instance(targetedBy(target)))), "Exile all cards from target player's hand and graveyard."));
	}
}
