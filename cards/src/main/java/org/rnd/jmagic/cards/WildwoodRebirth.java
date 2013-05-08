package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wildwood Rebirth")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class WildwoodRebirth extends Card
{
	public WildwoodRebirth(GameState state)
	{
		super(state);

		// Return target creature card from your graveyard to your hand.
		SetGenerator creaturesInYard = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
		SetGenerator target = targetedBy(this.addTarget(creaturesInYard, "target creature card from your graveyard"));
		this.addEffect(putIntoHand(target, You.instance(), "Return target creature card from your graveyard to your hand."));
	}
}
