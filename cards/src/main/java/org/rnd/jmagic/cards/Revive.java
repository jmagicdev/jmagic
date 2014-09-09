package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Revive")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class Revive extends Card
{
	public Revive(GameState state)
	{
		super(state);
		// Return target green card from your graveyard to your hand.

		Target target = this.addTarget(Intersect.instance(HasColor.instance(Color.GREEN), InZone.instance(GraveyardOf.instance(You.instance()))), "target green card from your graveyard");
		SetGenerator targetCard = targetedBy(target);

		this.addEffect(putIntoHand(targetCard, You.instance(), "Return target green card from your graveyard to your hand."));
	}
}
