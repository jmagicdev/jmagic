package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grave Exchange")
@Types({Type.SORCERY})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GraveExchange extends Card
{
	public GraveExchange(GameState state)
	{
		super(state);

		// Return target creature card from your graveyard to your hand. Target
		// player sacrifices a creature.
		SetGenerator target1 = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), "target creature card from your graveyard"));
		this.addEffect(putIntoHand(target1, You.instance(), "Return target creature card from your graveyard to your hand."));

		SetGenerator target2 = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(sacrifice(target2, 1, CreaturePermanents.instance(), "Target player sacrifices a creature."));
	}
}
